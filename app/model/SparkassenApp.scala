package model

import scala.slick.session.Database
import Database.threadLocalSession
import tools.EasyCryptUtil._

object SparkassenApp extends App {

  import DB.dal._
  import DB.dal.profile.simple._
  DB.db withSession {
    DB.dal.create

    val client = new SparKassenClient()
    val googleId = "17916981101.apps.googleusercontent.com".encrypt.encrypted
    val form = client.parseOverview(client.login(User.fromProperties))
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
