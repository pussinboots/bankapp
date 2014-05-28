package unit.org.stock.manager

import scala.slick.driver.H2Driver
import scala.slick.session.Database
import Database.threadLocalSession
import play.api.test.{FakeRequest, PlaySpecification}
import model.DateUtil
import scala.Some
import model.{DB, DAL, Balance, Stock, UserAccount}
import controllers.Application
import play.api.libs.json.Json

class ApplicationSpec extends PlaySpecification /*with SlickDbBefore*/ {
  sequential

  sys.props.+=("Database" -> "h2")
  import DB.dal._
  import DB.dal.profile.simple._

  implicit val googleId = Some("test googleId")
  val now = DateUtil.nowDateTimeOpt()
  val yesterday = Some(DateUtil.daysBeforDateTime(1))
  var userAccount: UserAccount = null
  initTestData(DB.db)

  //implicit def stringToOption(value: String) = Option(value)

  def initTestData(db: scala.slick.session.Database) {
    db.withSession {
      DB.dal.recreate
      userAccount = UserAccounts.insert(googleId.get, "testemail")

      Balances.insert(Balance(None, "Total", "100.01", now, Some(userAccount.googleId)))
      Balances.insert(Balance(None, "Girokonto", "281.21", yesterday, Some(userAccount.googleId)))
      Balances.insert(Balance(None, "Total", "100.01", yesterday, Some(UserAccounts.insert("other googleId", "testemail2").googleId)))

      Symbols.insertIfNotExists("Aktie 1", "CBK.DE")
      Symbols.insertIfNotExists("Aktie 2", "EOAN.DE")

      Stocks.insert(Stock(None, "Aktie 1", "11.01", now, Some(userAccount.googleId)))
      Stocks.insert(Stock(None, "Aktie 2", "28.21", yesterday, Some(userAccount.googleId)))
      Stocks.insert(Stock(None, "Aktie 1", "10.01", yesterday, Some(UserAccounts.insert("an other googleId", "testemail3").googleId)))
    }
  }

  "Application controller test" should {
    "rest call of findUserAccount (/rest/account)" in {
      DB.db withSession {
        import model.JsonHelper._
        val response = Application.findAccount(googleId.get)(FakeRequest())
        val jsonUserAccount = Json.fromJson[UserAccount](Json.parse(contentAsString(response)))
        status(response) must beEqualTo(OK)
        jsonUserAccount.get.googleId must beEqualTo(googleId.get)
        jsonUserAccount.get.id must beEqualTo(userAccount.id)
      }
    }
  }
}
