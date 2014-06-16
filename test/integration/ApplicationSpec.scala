package integration

import play.api.libs.ws._
import play.api.test._
import play.api.test.Helpers._

class ApplicationSpec extends PlaySpecification {
  "application setup should" should {
    "configured with custom keystore is enabled" in {
      running(FakeApplication()) {
        val keyStoreFile = System.getProperty("javax.net.ssl.keyStore")
        val keyStorePassword = System.getProperty("javax.net.ssl.keyStorePassword")
        keyStoreFile must beEqualTo("ssl/keystore")
        keyStorePassword must beEqualTo("")
      }
    }

    "configured with custom truststore is enabled" in {
      running(FakeApplication()) {
        val keyStoreFile = System.getProperty("javax.net.ssl.trustStore")
        val keyStorePassword = System.getProperty("javax.net.ssl.trustStorePassword")
        keyStoreFile must beEqualTo("ssl/cacerts")
        keyStorePassword must beEqualTo("")
      }
    }

    "configured with DB logging deactivate" in {
      running(FakeApplication()) {
        val logging = System.getProperty("com.mchange.v2.log.MLog")
        logging must beEqualTo(null)
      }
    }

    "check reditect to products.html work" in new WithServer(FakeApplication()) {
      val response = await(WS.url(s"http://localhost:$port/").withFollowRedirects(false).get)
      response.header("Location") must beEqualTo(Some(s"products.html"))
    }

    "configured to redirect all http request to https on heroku" in new WithServer(FakeApplication(additionalConfiguration=Map("enableDBSSL" -> "false"))) {
      val response = await(WS.url(s"http://localhost:$port/rest/balances").withFollowRedirects(false).withHeaders("x-forwarded-proto" -> "http").get)
      response.header("Location") must beEqualTo(Some(s"https://localhost:$port/rest/balances"))
    }
  }
}