package unit.org.stock.manager

import scala.slick.driver.H2Driver
import scala.slick.session.Database
import Database.threadLocalSession
import play.api.test.PlaySpecification
import model.DateUtil
import scala.Some
import model.{DB, DAL, Balance, Stock}

class ModelSpec extends PlaySpecification /*with SlickDbBefore*/ {
  sequential

  val db = DB.getSlickHSQLDatabase()
  val dao = new DAL(H2Driver)
  import dao._
  import dao.profile.simple._

  implicit val googleId = Some("test googleId")
  val now = DateUtil.nowDateTimeOpt()
  val yesterday = Some(DateUtil.daysBeforDateTime(1))
  initTestData(db)

  //implicit def stringToOption(value: String) = Option(value)

  def initTestData(db: scala.slick.session.Database) {
    db.withSession {
      dao.recreate
      val userAccount = UserAccounts.insert(googleId.get)

      Balances.insert(Balance(None, "Total", "100.01", now, Some(userAccount.googleId)))
      Balances.insert(Balance(None, "Girokonto", "281.21", yesterday, Some(userAccount.googleId)))
      Balances.insert(Balance(None, "Total", "100.01", yesterday, Some(UserAccounts.insert("other googleId").googleId)))

      Symbols.insertIfNotExists("Aktie 1", "CBK.DE")
      Symbols.insertIfNotExists("Aktie 2", "EOAN.DE")

      Stocks.insert(Stock(None, "Aktie 1", "11.01", now, Some(userAccount.googleId)))
      Stocks.insert(Stock(None, "Aktie 2", "28.21", yesterday, Some(userAccount.googleId)))
      Stocks.insert(Stock(None, "Aktie 1", "10.01", yesterday, Some(UserAccounts.insert("other googleId").googleId)))
    }
  }

  "UserAccount model test" should {
    "get the UserAccount for $googleId" in {
      db withSession {
        val q = UserAccounts.findByGoogle(googleId.get)
        val userAccount = q.first
        userAccount.googleId must beEqualTo(googleId.get)
      }
    }

    "not existing UserAccount for non existing googleId" in {
      db withSession {
        val userAccount = UserAccounts.findByGoogle("non existing googleId").firstOption
        userAccount must beEqualTo(None)
      }
    }
  }

  "Balance model test" should {
    "get one balanace for given googleId" in {
      db withSession {
        val balances = Balances.findByName("Total")(googleId).list
        balances.length must beEqualTo(1)
        balances(0).name must beEqualTo("Total")
        balances(0).date must beEqualTo(now)
        balances(0).value must beEqualTo("100.01")
        balances(0).googleId must beEqualTo(googleId)
      }
    }

    "empty balanaces for non existing googleId" in {
      db withSession {
        val balances = Balances.findByName("Total")(Some("not exists")).list
        balances.length must beEqualTo(0)
      }
    }

    "get all balanaces for given googleId" in {
      db withSession {
        val balances = Balances.findAll()(googleId).sortBy(_.date.asc).list
        balances.length must beEqualTo(2)
        balances(0).name must beEqualTo("Girokonto")
        balances(1).name must beEqualTo("Total")
        balances(0).value must beEqualTo("281.21")
        balances(1).value must beEqualTo("100.01")
        balances(0).date must beEqualTo(yesterday)
        balances(1).date must beEqualTo(now)
        balances(0).googleId must beEqualTo(googleId)
        balances(1).googleId must beEqualTo(googleId)
      }
    }
  }

  "Stock model test" should {
    "get one stock for given googleId" in {
      db withSession {
        val stocks = Stocks.findByName("Aktie 1")(googleId).list
        stocks.length must beEqualTo(1)
        stocks(0)._1.name must beEqualTo("Aktie 1")
        stocks(0)._1.value must beEqualTo("11.01")
        stocks(0)._1.date must beEqualTo(now)
        stocks(0)._1.googleId must beEqualTo(googleId)
      }
    }

    "empty stocks for non existing googleId" in {
      db withSession {
        val stocks = Stocks.findByName("Aktie 1")(Some("not exists")).list
        stocks.length must beEqualTo(0)
      }
    }

    "get all stocks for given googleId" in {
      db withSession {
        val stocks = Stocks.findAll()(googleId).sortBy(_._1.date.asc).list
        stocks.length must beEqualTo(2)
        stocks(0)._1.name must beEqualTo("Aktie 2")
        stocks(1)._1.name must beEqualTo("Aktie 1")
        stocks(0)._1.value must beEqualTo("28.21")
        stocks(1)._1.value must beEqualTo("11.01")
        stocks(0)._1.date must beEqualTo(yesterday)
        stocks(1)._1.date must beEqualTo(now)
        stocks(0)._1.googleId must beEqualTo(googleId)
        stocks(1)._1.googleId must beEqualTo(googleId)
      }
    }
  }
}
