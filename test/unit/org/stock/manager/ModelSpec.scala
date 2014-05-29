package unit.org.stock.manager

import scala.slick.driver.H2Driver
import scala.slick.session.Database
import Database.threadLocalSession
import play.api.test.PlaySpecification
import model.DateUtil
import scala.Some
import model.{DB, DAL, Balance, Stock}
import unit.org.stock.manager.test.DatabaseSetupBefore

class ModelSpec extends PlaySpecification with DatabaseSetupBefore {
  sequential

  import DB.dal._
  import DB.dal.profile.simple._

  "UserAccount model test" should {
    "get the UserAccount for $googleId" in {
      DB.db withSession {
        val q = UserAccounts.findByGoogle(googleId)
        val userAccount = q.first
        userAccount.googleId must beEqualTo(googleId)
      }
    }

    "not existing UserAccount for non existing googleId" in {
      DB.db withSession {
        val userAccount = UserAccounts.findByGoogle("non existing googleId").firstOption
        userAccount must beEqualTo(None)
      }
    }
  }

  "Balance model test" should {
    "get one balanace for given googleId" in {
      DB.db withSession {
        val balances = Balances.findByName("Total")(googleId).list
        balances.length must beEqualTo(1)
        balances(0).name must beEqualTo("Total")
        balances(0).date must beEqualTo(now)
        balances(0).value must beEqualTo("100.01")
        balances(0).googleId.get must beEqualTo(googleId)
      }
    }

    "empty balanaces for non existing googleId" in {
      DB.db withSession {
        val balances = Balances.findByName("Total")("not exists").list
        balances.length must beEqualTo(0)
      }
    }

    "get all balanaces for given googleId" in {
      DB.db withSession {
        val balances = Balances.findAll()(googleId).sortBy(_.date.asc).list
        balances.length must beEqualTo(2)
        balances(0).name must beEqualTo("Girokonto")
        balances(1).name must beEqualTo("Total")
        balances(0).value must beEqualTo("281.21")
        balances(1).value must beEqualTo("100.01")
        balances(0).date must beEqualTo(yesterday)
        balances(1).date must beEqualTo(now)
        balances(0).googleId.get must beEqualTo(googleId)
        balances(1).googleId.get must beEqualTo(googleId)
      }
    }
  }

  "Stock model test" should {
    "get one stock for given googleId" in {
      DB.db withSession {
        val stocks = Stocks.findByName("Aktie 1")(googleId).list
        stocks.length must beEqualTo(1)
        stocks(0)._1.name must beEqualTo("Aktie 1")
        stocks(0)._1.value must beEqualTo("11.01")
        stocks(0)._1.date must beEqualTo(now)
        stocks(0)._1.googleId.get must beEqualTo(googleId)
      }
    }

    "empty stocks for non existing googleId" in {
      DB.db withSession {
        val stocks = Stocks.findByName("Aktie 1")("not exists").list
        stocks.length must beEqualTo(0)
      }
    }

    "get all stocks for given googleId" in {
      DB.db withSession {
        val stocks = Stocks.findAll()(googleId).sortBy(_._1.date.asc).list
        stocks.length must beEqualTo(2)
        stocks(0)._1.name must beEqualTo("Aktie 2")
        stocks(1)._1.name must beEqualTo("Aktie 1")
        stocks(0)._1.value must beEqualTo("28.21")
        stocks(1)._1.value must beEqualTo("11.01")
        stocks(0)._1.date must beEqualTo(yesterday)
        stocks(1)._1.date must beEqualTo(now)
        stocks(0)._1.googleId.get must beEqualTo(googleId)
        stocks(1)._1.googleId.get must beEqualTo(googleId)
      }
    }
  }
}
