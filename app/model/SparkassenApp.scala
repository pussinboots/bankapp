package model

import scala.slick.session.Database
import Database.threadLocalSession
import tools.EasyCryptUtil

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
      Balances.insert(EasyCryptUtil.encrypt(name).encrypted, EasyCryptUtil.encrypt(account.value).encrypted)
    }
    Balances.insert(EasyCryptUtil.encrypt("Total").encrypted, EasyCryptUtil.encrypt(form.accounts.map(_._2.value).sum).encrypted)
    val stocks = client.parseStockOverview(client.stockOverview(form))
    println(stocks)
    for((name, account) <- stocks.accounts) {
      Stocks.insert(EasyCryptUtil.encrypt(name).encrypted, EasyCryptUtil.encrypt(account.value).encrypted)
    }
    Stocks.insert(EasyCryptUtil.encrypt("Total").encrypted, EasyCryptUtil.encrypt(stocks.accounts.map(_._2.value).sum).encrypted)
    //println(client.kontoDetails(form, "Das Girokonto\n**"))
  }
}
