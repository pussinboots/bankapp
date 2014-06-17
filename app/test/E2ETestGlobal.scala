package test

import play.api._
import model.DB
import global.Global

object E2ETestGlobal extends GlobalSettings {
    override def onStart(app: Application) {
      sys.props.+=("Database" -> "h2")
      val enableDBSSL = app.configuration.getBoolean("enableDBSSL").getOrElse(true)
      val enablePoolLogging = app.configuration.getBoolean("enablePoolLogging").getOrElse(false)
      if(!enableDBSSL) {
        System.clearProperty("javax.net.ssl.keyStore")
        System.clearProperty("javax.net.ssl.keyStorePassword")
        System.clearProperty("javax.net.ssl.trustStore")
        System.clearProperty("javax.net.ssl.trustStorePassword")
      }
      if(!enablePoolLogging) {
        System.clearProperty("com.mchange.v2.log.MLog")
        System.clearProperty("com.mchange.v2.log.FallbackMLog.DEFAULT_CUTOFF_LEVEL")
      }
      DB.db withSession {
      	SetupTestDatabase.insertTestData()
        SetupTestDatabase.insertE2ETestData()
      }
      Logger.debug("Application for e2e test has started")
    }
}