package integration

import play.api.libs.ws._
import play.api.test._
import play.api.test.Helpers._
import scala.slick.session.Database
import Database.threadLocalSession
import model.{DB, DAL, Balance, Stock}
import test.E2ETestGlobal
import play.api.Play
import play.api.Play.current

class E2ETestGlobalSpec extends PlaySpecification {
  val googleId  = "test googleId"
  val googleIdEnc  = "test googleid encrypted"
  "application configuration for e2e" should {
    "all test data for googleid test googleId" should {
      "user account exists" in { 
        running(FakeApplication()) {
          DB.db withSession {
            import DB.dal._
            import DB.dal.profile.simple._
            E2ETestGlobal.onStart(Play.application)
            DB.db withSession {
              val q = UserAccounts.findByGoogle(googleId)
              val userAccount = q.first
              userAccount.googleId must beEqualTo(googleId)
              userAccount.id must beEqualTo(Some(1))
            }
          }
        }
      }
      "balances exists" in {
        running(FakeApplication()) {
          import DB.dal._
          import DB.dal.profile.simple._
          E2ETestGlobal.onStart(Play.application)
          DB.db withSession {
            val balances = Balances.findAll()(googleId).sortBy(_.date.asc).list
            balances.length must beEqualTo(2)
            balances(0).name must beEqualTo("Girokonto")
            balances(1).name must beEqualTo("Total")
            balances(0).value must beEqualTo("281.21")
            balances(1).value must beEqualTo("100.01")
            balances(0).googleId.get must beEqualTo(googleId)
            balances(1).googleId.get must beEqualTo(googleId)
          }
        }
      }
      
      "stocks exists" in {
        running(FakeApplication()) {
          import DB.dal._
          import DB.dal.profile.simple._
          E2ETestGlobal.onStart(Play.application)
          DB.db withSession {
            val stocks = Stocks.findAll()(googleId).sortBy(_._1.date.asc).list
            stocks.length must beEqualTo(2)
            stocks(0)._1.name must beEqualTo("Aktie 2")
            stocks(1)._1.name must beEqualTo("Aktie 1")
            stocks(0)._1.value must beEqualTo("28.21")
            stocks(1)._1.value must beEqualTo("11.01")
            stocks(0)._1.googleId.get must beEqualTo(googleId)
            stocks(1)._1.googleId.get must beEqualTo(googleId)
          }
        }
      }
    }


    "all test data for googleid test googleid encrypted" should {
      "check that balances for the googleid encrypted exists" in {
        running(FakeApplication()) {
          import DB.dal._
          import DB.dal.profile.simple._
          E2ETestGlobal.onStart(Play.application)
          DB.db withSession {
            val balances = Balances.findAll()(googleIdEnc).sortBy(_.date.asc).list
            balances.length must beEqualTo(2)
            balances(0).name must beEqualTo("0uCwmlNo9baOU8Sp8CXqWg==")
            balances(1).name must beEqualTo("0uCwmlNo9baOU8Sp8CXqWg==")
            balances(0).value must beEqualTo("/Y6EQMQ3rSeOhtqhwuEZLA==")
            balances(1).value must beEqualTo("yta9QMvvmrhKvqtFw+OeTg==")
            balances(0).googleId.get must beEqualTo(googleIdEnc)
            balances(1).googleId.get must beEqualTo(googleIdEnc)
          }
        }
      }

      "check that user account for googleid encrypted exists" in {
        running(FakeApplication()) {
          DB.db withSession {
            import DB.dal._
            import DB.dal.profile.simple._
            E2ETestGlobal.onStart(Play.application)
            DB.db withSession {
              val q = UserAccounts.findByGoogle(googleIdEnc)
              val userAccount = q.first
              userAccount.googleId must beEqualTo(googleIdEnc)
              userAccount.id must beEqualTo(Some(4))
            }
          }
        }
      }
    }
  }
}