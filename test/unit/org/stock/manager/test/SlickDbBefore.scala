package unit.org.stock.manager.test

import org.specs2.mutable.Before
import model.DB
import scala.slick.session.Database
import Database.threadLocalSession

trait SlickDbBefore extends Before {
  //set h2 database for tests
  sys.props.+=("Database" -> "h2")

  override def before {
    val schema = "test"
    val db = DB.db
    db withSession DB.dal.recreate
    initTestData(db)
  }

  def initTestData(db: Database) {}
}
