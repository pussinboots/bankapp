package tools.imports

import scala.slick.session.Database
import Database.threadLocalSession
import tools.EasyCryptUtil._
import model.DB

object SparkassenApp extends App {

  require(sys.env.get("google_id") != None, "system property google_id is missing")
  start(sys.env.get("google_id").get, User.fromProperties)
  sys.exit(0)
  
  def start(googleId2: String, user: User) {
    import DB.dal._
    import DB.dal.profile.simple._
    DB.db withSession {
      
      val client = new SparKassenClient()
      implicit val googleId = googleId2     
      val form = client.parseOverview(client.login(user))
      println(form.accounts)
      println(form.accounts.map(_._2.value).sum)
      for((name, account) <- form.accounts) {
        Balances.insert(name.encrypt.encrypted, account.value.encrypt.encrypted)
      }
      Balances.insert("Total".encrypt.encrypted, form.accounts.map(_._2.value).sum.encrypt.encrypted)
      val stocks = client.parseStockOverview(client.stockOverview(form))
      println(stocks)
      for((name, account) <- stocks.accounts) {
        Stocks.insert(name.encrypt.encrypted, account.value.encrypt.encrypted)
      }
      Stocks.insert("Total".encrypt.encrypted, stocks.accounts.map(_._2.value).sum.encrypt.encrypted)
      //println(client.kontoDetails(form, "Das Girokonto\n**"))
    }
  }
}
