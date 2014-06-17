package test

import play.api._
import model.DB
import global.Global

object E2ETestGlobal extends GlobalSettings {
    override def onStart(app: Application) {
      sys.props.+=("Database" -> "h2")
      DB.db withSession {
      	SetupTestDatabase.insertTestData()
        SetupTestDatabase.insertE2ETestData()
      }
      Logger.info("Application for e2e test has started")
    }
}