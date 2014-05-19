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
import views.html
import play.api.mvc.WrappedRequest
import model.DB
import model.{Balance, Stock, StockJson, Symbol}
import scala.slick.session.Database
import Database.threadLocalSession
import scala.slick.lifted.ColumnOrdered
import DB.dal.profile.simple.{Query => SlickQuery}
import java.sql.Timestamp

object Application extends Controller {
  import DB.dal._
  import DB.dal.profile.simple._

  implicit val balanceWrites = Json.writes[Balance]
  implicit val stockWrites = Json.writes[Stock]
  implicit def tuple2Writes[A, B](implicit aWrites: Writes[A], bWrites: Writes[B]): Writes[Tuple2[A, B]] = new Writes[Tuple2[A, B]] {
	def writes(tuple: Tuple2[A, B]) = JsArray(Seq(aWrites.writes(tuple._1), bWrites.writes(tuple._2)))
  }
  implicit val stockAndSymbolWrites = Json.writes[StockJson]

  def index = Action {
    Results.MovedPermanently("assets/products.html")
  }

  def findBalances(name: String, sort: String, direction: String, items: Int) = Action { request =>
    DB.db withSession {
        def query = if (name.length > 0) Balances.findByName(name) else Balances.findAll()
        println(sorts(query, sort, direction).take(items).selectStatement)
    	def json = sorts(query, sort, direction).take(items).list()
    	Ok(Json.stringify(Json.toJson(json))) as ("application/json")
    }
  }

  def findStocks(name: String, date:Long, sort: String, direction: String, items: Int, page: Int) = Action { request =>
    DB.db withSession {
        var query = if (name.length > 0) Stocks.findByName(name) else Stocks.findAllWithJoin()
	query = if (date > -1) query.filter(_._1.date===new Timestamp(date)) else query
	//FIXME sorting is not working at the moment        
	println(query.drop(items * (page-1)).take(items).selectStatement)
    	def json = query.drop(items * (page-1)).take(items).list()
	def result = json map {case (stock: Stock, symbol: Option[String]) =>
		StockJson(stock.id.get, stock.name, stock.value, symbol.getOrElse(""), stock.date.get)	
	}    	
	Ok(Json.stringify(Json.toJson(result))) as ("application/json")
    }
  }

  def sorts[E <: Table[_], T](query: SlickQuery[E, T], sort: String, direction: String) = {
     var _query = query
     sort.reverse.split(" ") foreach{s=>	
	_query = _query.sortBy(sortKey(_, s.reverse, direction))
     }
     _query		
  }

  def sortKey[E <: Table[_]](e: E, sort: String, direction: String): ColumnOrdered[_] = direction match {
	    case "asc" => e.column[String](sort).asc
	    case "desc" => e.column[String](sort).desc
	  }
}
