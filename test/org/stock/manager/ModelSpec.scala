package org.stock.manager

import org.specs2.mutable.Specification
import play.api.test._
import play.api.test.Helpers._
import scala.slick.driver.{MySQLDriver, H2Driver}
import model.DB
import model.DAL
import scala.slick.session.Database
import Database.threadLocalSession
import play.api.test.PlaySpecification

class ModelSpec extends PlaySpecification {
  val db = DB.getSlickMysqlConnection()
  val dao = new DAL(MySQLDriver)

  import dao._
  import dao.profile.simple._

  "DB test" should {
    "create tables" in {
      db withSession {
        dao.create
        0 must beEqualTo(0)
      }
    }
    "insert symbols" in {
      db withSession {
        Symbols.insertIfNotExists("COMMERZBANK AG", "CBK.DE").symbol must beEqualTo("CBK.DE")
        Symbols.insertIfNotExists("E.ON SE NA", "EOAN.DE").symbol must beEqualTo("EOAN.DE")
        Symbols.insertIfNotExists("LBB-PRIVATDEPOT 3 (B)", "A1JSHG.DE").symbol must beEqualTo("A1JSHG.DE")
      }
    }
  }
}
