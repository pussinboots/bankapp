package controllers

import play.api.libs.json.Json
import play.api.mvc.Controller
import model.DB
import model.{Stock, StockJson}
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
