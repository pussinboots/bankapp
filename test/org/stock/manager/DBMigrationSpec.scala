package org.stock.manager

import org.specs2.mutable.Specification
import play.api.test._
import play.api.test.Helpers._
import scala.slick.driver.MySQLDriver
import model.DB
import model.DAL
import scala.slick.session.Database
import Database.threadLocalSession
import play.api.test.PlaySpecification

class DBMigrationSpec extends PlaySpecification {
  "DB migration" should {
    "create tables" in {
      val db = DB.getSlickMysqlConnection()
      val dao = new DAL(MySQLDriver)
      import dao._
      import dao.profile.simple._
      db withSession {
        dao.create
        Symbols.insert("COMMERZBANK AG", "CBK.DE")
        Symbols.insert("E.ON SE NA", "EOAN.DE")
        Symbols.insert("LBB-PRIVATDEPOT 3 (B)", "A1JSHG.DE")
        0 must beEqualTo(0)
      }
    }
  }
}