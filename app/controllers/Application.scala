package controllers

import play.api.libs.json._
import play.api.libs.functional.syntax._
import play.api.libs.iteratee.Enumerator
import play.api.libs.json.Json
import play.api.libs.json.Json.toJsFieldJsValueWrapper
import play.api.mvc.Action
import play.api.mvc.AnyContent
import play.api.mvc.BodyParser
import play.api.mvc.BodyParsers
import play.api.mvc.Controller
import play.api.mvc.Request
import play.api.mvc.Result
import play.api.mvc.Results
import play.api.mvc.Cookie
import views.html
import play.api.mvc.WrappedRequest
import model.DB
import model.{Balance, BalanceJson, BalanceJsonSave, Stock, StockJson, Symbol, UserAccount}
import scala.slick.session.Database
import Database.threadLocalSession
import scala.slick.lifted.ColumnOrdered
import DB.dal.profile.simple.{Query => SlickQuery}
import java.sql.Timestamp

object Helpers {

  import DB.dal._
  import DB.dal.profile.simple._

  implicit class QueryExtensionSort[E, T <: Table[E]](val query: Query[T, E]) {
    def sorts(sort: String, direction: String) = {
      var _query = query
      sort.reverse.split(" ").foreach { s =>
        println("sort " + s.reverse + " " + direction)
        _query = sortKey(s.reverse, direction)
      }
      _query
    }

    private def sortKey(sort: String, direction: String): Query[T, E] = {
      query.sortBy(table =>
        direction match {
          case "asc" =>
            println("sort2 " + sort + " asc")
            table.column[String](sort).asc
          case "desc" =>
            println("sort2 " + sort + " desc")
            table.column[String](sort).desc
        })
    }
  }

  implicit class QueryExtensionSort2[E, T <: Table[E]](val query: Query[(T, _), (E, Option[String])]) {
    def sorts(sort: String, direction: String) = {
      var _query = query
      sort.reverse.split(" ") foreach { s =>
        _query = sortKey(s.reverse, direction)
      }
      _query
    }

    private def sortKey(sort: String, direction: String): Query[(T, _), (E, Option[String])] = {
      query.sortBy(table =>
        direction match {
          case "asc" => table._1.column[String](sort).asc
          case "desc" => table._1.column[String](sort).desc
        })
    }
  }

}

/*case class Context[A](googleId: Option[String], request: Request[A])
  extends WrappedRequest(request)*/
case class JsonFmtListWrapper[T](items: List[T], count: Int)

object Application extends Controller {

  implicit object TimestampFormatter extends Format[Timestamp] {
    def reads(s: JsValue): JsResult[Timestamp] = JsSuccess(new Timestamp(s.toString.toLong))

    def writes(timestamp: Timestamp) = JsString(timestamp.toString)
  }

  implicit def listWrapperFormat[T: Format]: Format[JsonFmtListWrapper[T]] = (
    (__ \ "items").format[List[T]] and
      (__ \ "count").format[Int]
    )(JsonFmtListWrapper.apply, unlift(JsonFmtListWrapper.unapply))

  def ActionWithToken(f: Option[String] => Result) = Action { request => f(request.headers.get("X-AUTH-TOKEN"))}

  import DB.dal._
  import DB.dal.profile.simple._

  implicit val balanceWrites = Json.format[BalanceJson]
  implicit val balanceReads: Reads[BalanceJsonSave] = (
    (JsPath \ "name_enc").read[String] and
      (JsPath \ "value_enc").read[String]
    )(BalanceJsonSave.apply _)

  implicit val userWrites = Json.writes[UserAccount]
  /* implicit val stockWrites = Json.writes[Stock]*/

  import Helpers._

  implicit def tuple2Writes[A, B](implicit aWrites: Writes[A], bWrites: Writes[B]): Writes[Tuple2[A, B]] = new Writes[Tuple2[A, B]] {
    def writes(tuple: Tuple2[A, B]) = JsArray(Seq(aWrites.writes(tuple._1), bWrites.writes(tuple._2)))
  }

  implicit val stockAndSymbolWrites = Json.format[StockJson]

  def index = Action {
    Results.MovedPermanently("products.html")
  }

  def findBalances(name: String, sort: String, direction: String, items: Int, page: Int) = ActionWithToken { implicit googleId =>
    DB.db withSession {
      def query = if (name.length > 0) Balances.findByName(name) else Balances.findAll()(googleId)
      println(query.sorts(sort, direction).take(items).selectStatement)
      val json = query.sorts(sort, direction).drop(items * (page - 1)).take(items).list()
      val count = query.list.length
      val result = json map { case (balance: Balance) =>
        BalanceJson(balance.id.get, balance.name, balance.value, balance.date.get)
      }
      import JsonFmtListWrapper._
      Ok(Json.stringify(Json.toJson(JsonFmtListWrapper(result, count)))) as ("application/json")
    }
  }

  def saveBalance() = Action(BodyParsers.parse.json) { implicit request =>
    implicit val googleId = request.headers.get("X-AUTH-TOKEN")
    val balance = request.body.validate[BalanceJsonSave]
    balance.fold(
      errors => {
        BadRequest(Json.obj("status" -> "KO", "message" -> JsError.toFlatJson(errors)))
      },
      balance => {
        DB.db withSession {
          Balances.insert(balance.name_enc, balance.value_enc)
        }
        Ok(Json.obj("status" -> "OK", "message" -> ("Balance '" + balance.name_enc + "' saved.")))
      }
    )
  }

  def findAccount(googleId: String) = Action { request =>
    DB.db withSession {
      def query = UserAccounts.findByGoogle(googleId)
      def json = query.firstOption
      Ok(Json.stringify(Json.toJson(json))) /*.withCookies(Cookie("XSRF-TOKEN", googleId))*/ as ("application/json")
    }
  }

  def findStocks(name: String, date: Long, sort: String, direction: String, items: Int, page: Int) = ActionWithToken { implicit googleId =>
    DB.db withSession {
      var query = if (name.length > 0) Stocks.findByName(name) else Stocks.findAllWithJoin()
      query = if (date > -1) query.filter(_._1.date === new Timestamp(date)) else query
      //FIXME sorting is not working at the moment
      println(query.drop(items * (page - 1)).take(items).selectStatement)
      val json = query.sorts(sort, direction).drop(items * (page - 1)).take(items).list()
      val count = query.list.length
      val result = json map { case (stock: Stock, symbol: Option[String]) =>
        StockJson(stock.id.get, stock.name, stock.value, symbol.getOrElse(""), stock.date.get)
      }
      Ok(Json.stringify(Json.toJson(JsonFmtListWrapper(result, count)))) as ("application/json")
    }
  }
}
