package controllers

import play.api.mvc.Action
import play.api.mvc.Controller
import play.api.mvc.Results

object Application extends Controller {
  def index = Action {
    Results.MovedPermanently("products.html")
  }
}
