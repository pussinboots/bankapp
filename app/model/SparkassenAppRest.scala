package model

import tools.EasyCryptUtil._
import play.api.libs.ws._
import scala.concurrent.Future
import play.api.libs.json._

object SparkassenAppRest extends App {

  val endPoint = "https://bana.heroku.com"
  val client = new SparKassenClient()
  require(sys.env.get("google_id") != None, "system property google_id is missing")
  val googleId = sys.env.get("google_id").get
  val form = client.parseOverview(client.login(User.fromProperties))
  println(form.accounts)
  println(form.accounts.map(_._2.value).sum)
  for ((name, account) <- form.accounts) {
    val data = Json.obj(
      "name_enc" -> name.encrypt.encrypted,
      "value_enc" -> account.value.encrypt.encrypted
    )
    WS.url(s"$endPoint/rest/balances").withHeaders("X-AUTH-TOKEN" -> googleId).withHeaders("Content-Type" -> "application/json").post(data)
  }
  var data = Json.obj(
    "name_enc" -> "Total".encrypt.encrypted,
    "value_enc" -> form.accounts.map(_._2.value).sum.encrypt.encrypted
  )
  WS.url(s"$endPoint/rest/balances").withHeaders("X-AUTH-TOKEN" -> googleId).withHeaders("Content-Type" -> "application/json").post(data)
  val stocks = client.parseStockOverview(client.stockOverview(form))
  println(stocks)
  for((name, account) <- stocks.accounts) {
    val data = Json.obj(
      "name_enc" -> name.encrypt.encrypted,
      "value_enc" -> account.value.encrypt.encrypted
    )
    WS.url(s"$endPoint/rest/stocks").withHeaders("X-AUTH-TOKEN" -> googleId).withHeaders("Content-Type" -> "application/json").post(data)
  }
  data = Json.obj(
    "name_enc" -> "Total".encrypt.encrypted,
    "value_enc" -> stocks.accounts.map(_._2.value).sum.encrypt.encrypted
  )
  WS.url(s"$endPoint/rest/stocks").withHeaders("X-AUTH-TOKEN" -> googleId).withHeaders("Content-Type" -> "application/json").post(data)
}
