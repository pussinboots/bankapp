package integration

import play.api.GlobalSettings
import play.api.test._
import play.api.libs.ws._
import unit.org.stock.manager.test.{Betamax, DatabaseSetupBefore}
import play.api.libs.json.Json
import model.{DB, DAL, UserAccount}
import model.JsonHelper.JsonFmtListWrapper
import co.freeside.betamax.TapeMode
import controllers.GoogleController
import scala.slick.session.Database
import Database.threadLocalSession

class GoogleControllerSpec extends PlaySpecification with DatabaseSetupBefore {
  sequential

  import DB.dal._
  import DB.dal.profile.simple._
  import model.JsonHelper._
  "/rest/account/" should {
    "given valid googleId return valid account" in {
      DB.db withSession {
        val response = GoogleController.findAccount(googleId)(FakeRequest())
        val jsonUserAccount = Json.fromJson[UserAccount](Json.parse(contentAsString(response)))
        status(response) must beEqualTo(OK)
        jsonUserAccount.get.googleId must beEqualTo(googleId)
        jsonUserAccount.get.id must beEqualTo(userAccount.id)
      }
    }

    "given valid googleIdEnc return valid account" in {
      DB.db withSession {
        val response = GoogleController.findAccount("test googleid encrypted")(FakeRequest())
        val jsonUserAccount = Json.fromJson[UserAccount](Json.parse(contentAsString(response)))
        status(response) must beEqualTo(OK)
        jsonUserAccount.get.googleId must beEqualTo("test googleid encrypted")
        jsonUserAccount.get.id must beEqualTo(Some(4))
      }
    }

    "given invalid googleId return bad request" in {
      DB.db withSession {
        val response = GoogleController.findAccount("invalid googleId")(FakeRequest())
        val errorJson = Json.parse(contentAsString(response))
        status(response) must beEqualTo(BAD_REQUEST)
        val errorMsg = (errorJson \ "error").as[String]
        val errorDesc = (errorJson \ "error_description").as[String]
        val debugInfo = (errorJson \ "debug_info").as[String]
        errorMsg must beEqualTo("invalid_googleid")
        errorDesc must beEqualTo("Invalid Value")
        debugInfo must startWith("code: INVALID_VALUE\nhttp status: 400\narguments: [invalid_googleId]")
      }
    }
  }

  "/rest/google should" should {
    "given invalid token return bad request" in Betamax(tape="googleSignIn", mode=Some(TapeMode.READ_ONLY)) {
      DB.db withSession {
        running(FakeApplication(additionalConfiguration=Map("enableDBSSL" -> "false"))) {
          val response = GoogleController.googleConnect("inValidToken")(FakeRequest())
          val errorJson = Json.parse(contentAsString(response))
          status(response) must beEqualTo(BAD_REQUEST)
          val errorMsg = (errorJson \ "error").as[String]
          val errorDesc = (errorJson \ "error_description").as[String]
          val debugInfo = (errorJson \ "debug_info").as[String]
          errorMsg must beEqualTo("invalid_token")
          errorDesc must beEqualTo("Invalid Value")
          debugInfo must startWith("code: INVALID_VALUE\nhttp status: 400\narguments: [invalid_token]\ncause: com.google.security")
        }
      }
    }

    "given valid token return new created user account as json" in Betamax(tape="googleSignIn", mode=Some(TapeMode.READ_ONLY)) {
      DB.db withSession {
        running(FakeApplication(additionalConfiguration=Map("enableDBSSL" -> "false"))) {
          val useAccountNotExists = UserAccounts.findByGoogle("12345678910").firstOption
          useAccountNotExists must beEqualTo(None)
          val response = GoogleController.googleConnect("ya29.JQCbAEBX9v9sWSAAAAB3ztWIf69L6Om_Ck0UGA0Tlemrgoatzq0xcAOhjTPG1A")(FakeRequest())
          val jsonUserAccount = Json.fromJson[UserAccount](Json.parse(contentAsString(response)))
          status(response) must beEqualTo(OK)
          jsonUserAccount.get.googleId must beEqualTo("12345678910")
          jsonUserAccount.get.eMail must beEqualTo("test@googlemail.com")
          //check if the valid user account was registerd
          val useAccountCreated = UserAccounts.findByGoogle("12345678910").firstOption
          useAccountCreated must beSome[UserAccount]
          useAccountCreated.get.eMail must beEqualTo("test@googlemail.com")
        }
      }
    }
  }
}
