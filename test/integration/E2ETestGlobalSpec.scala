package integration

import play.api.libs.ws._
import play.api.test._
import play.api.test.Helpers._
import scala.slick.session.Database
import Database.threadLocalSession
import model.{DB, DAL, Balance}
import test.E2ETestGlobal
import play.api.Play
import play.api.Play.current

class E2ETestGlobalSpec extends PlaySpecification {
  val googleId  = "test googleId"
  val googleIdEnc  = "test googleid encrypted"
  "application configuration for e2e" should {
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

    "check that user account for googleid without encryption exists" in 
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

    "check that user account for googleid encrypted exists" in 
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