package controllers

import play.api.libs.json.Json
import play.api.mvc.Action
import play.api.mvc.Controller
import play.api.mvc.Results
import model.DB
import model.{Stock, StockJson}
import scala.slick.session.Database
import Database.threadLocalSession
import DB.dal.profile.simple.{Query => SlickQuery}
import java.sql.Timestamp
import play.api.libs.ws.WS

object Application extends Controller {

  import ControllerHelpers._
  import DB.dal._
  import DB.dal.profile.simple._

  import model.SlickHelpers._
  import model.JsonHelper._

  def index = Action {
    Results.MovedPermanently("products.html")
  }

  def findAccount(googleId: String) = Action { request =>
    DB.db withSession {
      def query = UserAccounts.findByGoogle(googleId)
      def json = query.firstOption
      Ok(Json.stringify(Json.toJson(json))) /*.withCookies(Cookie("XSRF-TOKEN", googleId))*/ as ("application/json")
    }
  }

  def googleConnect(token: String) = Action.async { request =>
    implicit val context = scala.concurrent.ExecutionContext.Implicits.global
    //TODO make the google token url configurable
    WS.url("https://www.googleapis.com/oauth2/v1/tokeninfo?access_token=" + token).get()
      .map { response =>
      response.status match {
        case OK => DB.db withSession {
          val googleId = response.json \ "user_id"
          val eMail = response.json \ "email"
          val user = UserAccounts.findOrCreateByGoogleId(googleId.as[String], eMail.as[String])
          Ok(Json.stringify(Json.toJson(user)))
        }
        case x => new Status(x)(response.body)
      }
    }
  }
}
