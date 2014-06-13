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
}
