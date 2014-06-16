package controllers

import play.api.libs.json._
import play.api.libs.json.Json
import play.api.libs.json.Json.toJsFieldJsValueWrapper
import play.api.mvc.Controller
import play.api.mvc.BodyParsers
import model.DB
import model.{Stock, StockJson, StockJsonSave}
import scala.slick.session.Database
import Database.threadLocalSession
import DB.dal.profile.simple.{Query => SlickQuery}
import java.sql.Timestamp

object StockController extends Controller {

  import controllers.ControllerHelpers._
  import DB.dal._
  import DB.dal.profile.simple._
  import model.SlickHelpers._
  import model.JsonHelper._

  def findStocks(name: String, date: Long, sort: String, direction: String, items: Int, page: Int) = ActionWithToken { (request, googleId) =>
    DB.db withSession {
      var query = if (name.length > 0) Stocks.findByName(name)(googleId) else Stocks.findAll()(googleId)
      query = if (date > -1) query.filter(_._1.date === new Timestamp(date)) else query
      //FIXME sorting is not working at the moment
      //println(query.drop(items * (page - 1)).take(items).selectStatement)
      val json = query.sorts(sort, direction).drop(items * (page - 1)).take(items).list()
      val count = query.list.length
      val result = json map { case (stock: Stock, symbol: Option[String]) =>
        StockJson(stock.id.get, stock.name, stock.value, symbol.getOrElse(""), stock.date.get)
      }
      Ok(Json.stringify(Json.toJson(JsonFmtListWrapper(result, count)))) as ("application/json")
    }
  }

  def saveStock() = ActionWithToken(BodyParsers.parse.json) {
    (request, googleId) =>
      val stock = request.body.validate[StockJsonSave]
      stock.fold(
        errors => {
          BadRequest(Json.obj("status" -> "KO", "message" -> JsError.toFlatJson(errors)))
        },
        stock => {
          DB.db withSession {
            Stocks.insert(stock.name_enc, stock.value_enc)(googleId)
          }
          Ok(Json.obj("status" -> "OK", "message" -> ("Stock '" + stock.name_enc + "' saved.")))
        }
      )
  }
}
