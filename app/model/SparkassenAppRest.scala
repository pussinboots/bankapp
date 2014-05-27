package model

import tools.EasyCryptUtil._
import play.api.libs.ws._
import scala.concurrent.Future
import play.api.libs.json._

object SparkassenAppRest extends App {

  val endPoint = "http://localhost:9000/rest/balances"
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
    WS.url(endPoint).withHeaders("X-AUTH-TOKEN" -> googleId).withHeaders("Content-Type" -> "application/json").post(data)

    //      Balances.insert(name.encrypt.encrypted, account.value.encrypt.encrypted)
  }
  val data = Json.obj(
    "name_enc" -> "Total".encrypt.encrypted,
    "value_enc" -> form.accounts.map(_._2.value).sum.encrypt.encrypted
  )
  WS.url(endPoint).withHeaders("X-AUTH-TOKEN" -> googleId).withHeaders("Content-Type" -> "application/json").post(data)
  //Balances.insert("Total".encrypt.encrypted, form.accounts.map(_._2.value).sum.encrypt.encrypted)
  /*    val stocks = client.parseStockOverview(client.stockOverview(form))
      println(stocks)
      for((name, account) <- stocks.accounts) {
        Stocks.insert(name.encrypt.encrypted, account.value.encrypt.encrypted)
      }
      Stocks.insert("Total".encrypt.encrypted, stocks.accounts.map(_._2.value).sum.encrypt.encrypted)*/
  //println(client.kontoDetails(form, "Das Girokonto\n**"))
}
