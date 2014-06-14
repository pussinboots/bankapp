package test

import model.{UserAccount, DateUtil, Balance, Stock, DB}
import scala.slick.session.Database
import Database.threadLocalSession

object SetupTestDatabase {

  var now = DateUtil.nowDateTimeOpt()
  var yesterday = Option(DateUtil.daysBeforDateTime(1))
  var userAccount: UserAccount = null
  import DB.dal._
  import DB.dal.profile.simple._
  def insertTestData(googleId: String = "test googleId") = {
    DB.dal.recreate
    userAccount = UserAccounts.insert(googleId, "testemail")
    //TimeStamp could be rounded by database so fetch the rounded value back
    now = Balances.insert(Balance(None, "Total", "100.01", now, Some(userAccount.googleId))).date
    yesterday = Balances.insert(Balance(None, "Girokonto", "281.21", yesterday, Some(userAccount.googleId))).date
    Balances.insert(Balance(None, "Total", "100.01", yesterday, Some(UserAccounts.insert("other googleId", "testemail2").googleId)))

    Symbols.insertIfNotExists("Aktie 1", "CBK.DE")
    Symbols.insertIfNotExists("Aktie 2", "EOAN.DE")

    Stocks.insert(Stock(None, "Aktie 1", "11.01", now, Some(userAccount.googleId)))
    Stocks.insert(Stock(None, "Aktie 2", "28.21", yesterday, Some(userAccount.googleId)))
    Stocks.insert(Stock(None, "Aktie 1", "10.01", yesterday, Some(UserAccounts.insert("an other googleId", "testemail3").googleId)))
    (now, yesterday, userAccount)
  }
}