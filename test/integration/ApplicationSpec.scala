package integration

import play.api.GlobalSettings
import play.api.test._

class ApplicationSpec extends PlaySpecification {
  "application setup should" should {
    "setup with custom keystore is enabled" in {
      running(FakeApplication()) {
        val keyStoreFile = System.getProperty("javax.net.ssl.keyStore")
        val keyStorePassword = System.getProperty("javax.net.ssl.keyStorePassword")
        keyStoreFile must beEqualTo("ssl/keystore")
        keyStorePassword must beEqualTo("")
      }
    }

    "setup with custom truststore is enabled" in {
      running(FakeApplication()) {
        val keyStoreFile = System.getProperty("javax.net.ssl.trustStore")
        val keyStorePassword = System.getProperty("javax.net.ssl.trustStorePassword")
        keyStoreFile must beEqualTo("ssl/cacerts")
        keyStorePassword must beEqualTo("")
      }
    }
  }
}

