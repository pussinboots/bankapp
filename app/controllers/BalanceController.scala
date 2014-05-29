package controllers

import play.api.libs.json._
import play.api.libs.json.Json
import play.api.libs.json.Json.toJsFieldJsValueWrapper
import play.api.mvc.BodyParsers
import play.api.mvc.Controller
import model.DB
import model.{Balance, BalanceJson, BalanceJsonSave}
import scala.slick.session.Database
import Database.threadLocalSession
import DB.dal.profile.simple.{Query => SlickQuery}
import java.sql.Timestamp

object BalanceController extends Controller {

  import controllers.ControllerHelpers._
  import DB.dal._
  import DB.dal.profile.simple._
  import model.SlickHelpers._
  import model.JsonHelper._

  def findBalances(name: String, date: Long, sort: String, direction: String, items: Int, page: Int) = ActionWithToken { (request, googleId) =>    DB.db withSession {
      //TODO the googleId should be implicit
      var query = if (name.length > 0) Balances.findByName(name)(googleId) else Balances.findAll()(googleId)
      query = if (date > -1) query.filter(_.date === new Timestamp(date)) else query
      println(query.sorts(sort, direction).take(items).selectStatement)
      val json = query.sorts(sort, direction).drop(items * (page - 1)).take(items).list()
      val count = query.list.length
      val result = json map { case (balance: Balance) =>
        BalanceJson(balance.id.get, balance.name, balance.value, balance.date.get)
      }
      Ok(Json.stringify(Json.toJson(JsonFmtListWrapper(result, count)))) as ("application/json")
    }
  }

  def saveBalance() = ActionWithToken(BodyParsers.parse.json) {
    (request, googleId) =>
      val balance = request.body.validate[BalanceJsonSave]
      balance.fold(
        errors => {
          BadRequest(Json.obj("status" -> "KO", "message" -> JsError.toFlatJson(errors)))
        },
        balance => {
          DB.db withSession {
            Balances.insert(balance.name_enc, balance.value_enc)(googleId)
          }
          Ok(Json.obj("status" -> "OK", "message" -> ("Balance '" + balance.name_enc + "' saved.")))
        }
      )
  }
}
