package org.stock.manager

import org.specs2.mutable.Specification
import play.api.test._
import play.api.test.Helpers._
import scala.slick.driver.H2Driver
import scala.slick.session.Database
import Database.threadLocalSession
import play.api.test.PlaySpecification
import org.stock.manager.test.SlickDbBefore
import model.DateUtil
import scala.Some
import scala.Some
import model.Balance
import model.DB
import model.DAL

class ModelSpec extends PlaySpecification /*with SlickDbBefore*/ {
  sequential

  val db = DB.getSlickHSQLDatabase()
  val dao = new DAL(H2Driver)
  import dao._
  import dao.profile.simple._

  implicit val googleId = Some("test googleId")
  val now = DateUtil.nowDateTimeOpt()
  initTestData(db)

  //implicit def stringToOption(value: String) = Option(value)

  def initTestData(db: scala.slick.session.Database) {
    db.withSession {
      dao.recreate
      val userAccount = UserAccounts.insert(googleId.get)
      Balances.insert(Balance(None, "Total", "100.01", now, Some(userAccount.googleId)))
      Balances.insert(Balance(None, "Total", "100.01", now, Some(UserAccounts.insert("other googleId").googleId)))
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
        val balances = Balances.findAll()(googleId).list
        balances.length must beEqualTo(1)
        balances(0).name must beEqualTo("Total")
        balances(0).date must beEqualTo(now)
        balances(0).googleId must beEqualTo(googleId)
      }
    }
  }
}
