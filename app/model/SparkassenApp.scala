package model

import scala.slick.session.Database
import Database.threadLocalSession

object SparkassenApp extends App {

  import DB.dal._
  import DB.dal.profile.simple._
  DB.db withSession {
    DB.dal.create

    val client = new SparKassenClient()
    val form = client.parseOverview(client.login(User.fromProperties))
    println(form.accounts)
    println(form.accounts.map(_._2.value).sum)
    for((name, account) <- form.accounts) {
      Balances.insert(name, account.value)
    }
    Balances.insert("Total", form.accounts.map(_._2.value).sum)
    val stocks = client.parseStockOverview(client.stockOverview(form))
    println(stocks)
    for((name, account) <- stocks.accounts) {
      Stocks.insert(name, account.value)
    }
    Stocks.insert("Total", stocks.accounts.map(_._2.value).sum)
    //println(client.kontoDetails(form, "Das Girokonto\n**"))
  }
}
