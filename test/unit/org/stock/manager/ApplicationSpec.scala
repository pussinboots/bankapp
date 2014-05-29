package unit.org.stock.manager

import scala.slick.driver.H2Driver
import scala.slick.session.Database
import Database.threadLocalSession
import play.api.test.{FakeRequest, PlaySpecification}
import model.DateUtil
import scala.Some
import model.{DB, DAL, Balance, Stock, UserAccount}
import controllers.Application
import play.api.libs.json.Json
import unit.org.stock.manager.test.DatabaseSetupBefore

class ApplicationSpec extends PlaySpecification with DatabaseSetupBefore {
  sequential

  "Application controller test" should {
    "rest call of findUserAccount (/rest/account/)" in {
      import DB.dal._
      import DB.dal.profile.simple._
      DB.db withSession {
        import model.JsonHelper._
        val response = Application.findAccount(googleId)(FakeRequest())
        val jsonUserAccount = Json.fromJson[UserAccount](Json.parse(contentAsString(response)))
        status(response) must beEqualTo(OK)
        jsonUserAccount.get.googleId must beEqualTo(googleId)
        jsonUserAccount.get.id must beEqualTo(userAccount.id)
      }
    }
  }
}
