package unit.org.stock.manager

import scala.slick.session.Database
import Database.threadLocalSession
import play.api.test.{FakeApplication, FakeRequest, PlaySpecification}
import model.DateUtil
import scala.Some
import model.{DB, DAL, UserAccount}
import controllers.Application
import play.api.libs.json.Json
import unit.org.stock.manager.test.DatabaseSetupBefore
import unit.org.stock.manager.test.Betamax
import co.freeside.betamax.TapeMode
import co.freeside.betamax.httpclient.BetamaxHttpsSupport
import play.api.libs.ws.WS

class ApplicationSpec extends PlaySpecification with DatabaseSetupBefore {
  sequential
  sys.props.+=("com.ning.http.client.AsyncHttpClientConfig.useProxyProperties" -> "true")

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
