package integration

import model.{DB, DAL, Balance, Stock}
import test.E2ETestGlobal
import play.api.Play
import play.api.Play.current
import play.api.test._
import play.api.test.Helpers._
import org.specs2.execute.AsResult
import org.specs2.matcher.DataTables
import org.specs2.mutable.Before

import scala.slick.session.Database
import Database.threadLocalSession


class E2ETestGlobalSpec extends PlaySpecification with DataTables {
  sys.props.+=("Database" -> "h2")
  import DB.dal._
  import DB.dal.profile.simple._
      
  val googleId  = "test googleId"
  val googleIdEnc  = "test googleid encrypted"

  def around[T: AsResult](f: => T) = {
    running(FakeApplication()) {
      E2ETestGlobal.onStart(Play.application)
      DB.db withSession {
        AsResult(f)
      }
    }
  }

  "teat data configuration for e2e" should {
    "all test users exists" in { 
        "id" | "googleId"   |
        1    ! googleId     |
        4    ! googleIdEnc  |> { (id, gid)=>around{
            val q = UserAccounts.findByGoogle(gid)
            val userAccount = q.first
            userAccount.googleId must beEqualTo(gid)
            userAccount.id must beEqualTo(Some(id))
          }
        }
    }
    "all balances exists" in {
      "googleId"       |  "idx" | "name"                       |  "value"                    |
      googleId        !!   0    ! "Girokonto"                 !! "281.21"                    |
      googleId        !!   1    ! "Total"                     !! "100.01"                    |
      googleIdEnc     !!   0    ! "0uCwmlNo9baOU8Sp8CXqWg=="  !! "/Y6EQMQ3rSeOhtqhwuEZLA=="  |
      googleIdEnc     !!   1    ! "0uCwmlNo9baOU8Sp8CXqWg=="  !! "yta9QMvvmrhKvqtFw+OeTg=="  |> 
      { (gid, idx, name, value)=> around {
          val balances = Balances.findAll()(gid).sortBy(_.date.asc).list
          balances.length must beEqualTo(2)
          balances(idx).name must beEqualTo(name)
          balances(idx).value must beEqualTo(value)
          balances(idx).googleId.get must beEqualTo(gid)
        }
      }
    }
    
    "all stocks exists" in {
      "googleId"       |  "idx" | "name"                        |  "value"                   |
      googleId        !!   0    ! "Aktie 2"                    !! "28.21"                    |
      googleId        !!   1    ! "Aktie 1"                    !! "11.01"                    |> 
      { (gid, idx, name, value)=> around {
          val stocks = Stocks.findAll()(gid).sortBy(_._1.date.asc).list
          stocks.length must beEqualTo(2)
          stocks(idx)._1.name must beEqualTo(name)
          stocks(idx)._1.value must beEqualTo(value)
          stocks(idx)._1.googleId.get must beEqualTo(gid)
        }
      }
    }
  }
}