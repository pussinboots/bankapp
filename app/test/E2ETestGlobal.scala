package test

import play.api._
import model.DB
import global.Global

object E2ETestGlobal extends GlobalSettings
{
    override def onStart(app: Application) {
      val enableDBSSL = app.configuration.getBoolean("enableDBSSL").getOrElse(true)
      val enablePoolLogging = app.configuration.getBoolean("enablePoolLogging").getOrElse(false)
      if(enableDBSSL) {
        Logger.info("set custom truststore for cleardb mysql ssl connections")
        DB.WithSSL()
      }
      if(enablePoolLogging) {
        DB.WithPoolLogging()
      }
      sys.props.+=("Database" -> "h2")
      DB.db withSession {
      	SetupTestDatabase.insertTestData()
      }
      Logger.info("Application for e2e test has started")
    }
    override def onStop(app: Application) {
      Logger.info("Application of e2e test shutdown...")
    }
}