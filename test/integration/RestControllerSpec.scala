package integration

import org.specs2.mutable.Specification
import play.api.test._
import play.api.test.Helpers._
import play.api.libs.ws._
import unit.org.stock.manager.test.DatabaseSetupBefore
import play.api.libs.json.Json
import model.{StockJsonSave, BalanceJsonSave, DateUtil, BalanceJson, StockJson}
import model.JsonHelper.JsonFmtListWrapper

class RestControllerSpec extends PlaySpecification with DatabaseSetupBefore {
  sequential
  import model.JsonHelper._

  "/rest/balances should" should {
    "given no x-auth-token header then return bad request" in new WithServer {
      val response = await(WS.url(s"http://localhost:$port/rest/balances").get)
      response.status must equalTo(BAD_REQUEST)
      response.json must equalTo(Json.obj("status" -> "MH", "message" -> "missing X-AUTH-TOKEN http header"))
    }

    "given x-auth-header" should {

      "then return first two balances sort by id asc" in new WithServer {
        implicit val response = await(WS.url(s"http://localhost:$port/rest/balances").withHeaders("X-AUTH-TOKEN" -> googleId).get)
        implicit val balances = Json.fromJson[JsonFmtListWrapper[BalanceJson]](response.json).get
        thenTwoResults[BalanceJson]{balanceList=>
          checkFirstBalance(balanceList(0))
          checkSecondBalance(balanceList(1))
        }
      }

      "given sort by date desc then return two balances" in new WithServer {
        implicit val response = await(WS.url(s"http://localhost:$port/rest/balances?sort=date&direction=desc").withHeaders("X-AUTH-TOKEN" -> googleId).get)
        implicit val balances = Json.fromJson[JsonFmtListWrapper[BalanceJson]](response.json).get
        thenTwoResults[BalanceJson]{balanceList=>
          checkFirstBalance(balanceList(0))
          checkSecondBalance(balanceList(1))
        }
      }

      "given sort by date asc then return two balances" in new WithServer {
        implicit val response = await(WS.url(s"http://localhost:$port/rest/balances?sort=date&direction=asc").withHeaders("X-AUTH-TOKEN" -> googleId).get)
        implicit val balances = Json.fromJson[JsonFmtListWrapper[BalanceJson]](response.json).get
        thenTwoResults[BalanceJson]{balanceList=>
          checkSecondBalance(balanceList(0))
          checkFirstBalance(balanceList(1))
        }
      }

      "given items eq one then return first two balances sort by id asc" in new WithServer {
        implicit val response = await(WS.url(s"http://localhost:$port/rest/balances?items=1").withHeaders("X-AUTH-TOKEN" -> googleId).get)
        implicit val balances = Json.fromJson[JsonFmtListWrapper[BalanceJson]](response.json).get
        thenOneResultTwoCounts[BalanceJson](balanceList=>checkFirstBalance(balanceList(0)))
      }

      "given name eq 'Total' then return one balance" in new WithServer {
        implicit val response = await(WS.url(s"http://localhost:$port/rest/balances?name_enc=Total").withHeaders("X-AUTH-TOKEN" -> googleId).get)
        implicit val balances = Json.fromJson[JsonFmtListWrapper[BalanceJson]](response.json).get
        thenOneResult[BalanceJson](balanceList=>checkFirstBalance(balanceList(0)))
      }

      "given date eq now then return one balance" in new WithServer {
        implicit val response = await(WS.url(s"http://localhost:$port/rest/balances?date=${now.get.getTime}").withHeaders("X-AUTH-TOKEN" -> googleId).get)
        implicit val balances = Json.fromJson[JsonFmtListWrapper[BalanceJson]](response.json).get
        thenOneResult[BalanceJson](balanceList=>checkFirstBalance(balanceList(0)))
      }

      "save balance" should {
        "given no x-auth-token header then return in bad request" in new WithServer {
          val balanceToSave = BalanceJsonSave("New Entry", "1000.00")
          val response = await(WS.url(s"http://localhost:$port/rest/balances")
            .post(Json.toJson(balanceToSave)))
          response.status must equalTo(BAD_REQUEST)
          response.json must equalTo(Json.obj("status" -> "MH", "message" -> "missing X-AUTH-TOKEN http header"))
        }

        "given invalid json then return bad request" in new WithServer {
          val response = await(WS.url(s"http://localhost:$port/rest/balances")
            .withHeaders("X-AUTH-TOKEN" -> googleId)
            .withHeaders("Content-Type" -> "application/json")
            .post("invalid json"))
          response.status must equalTo(BAD_REQUEST)
          response.body must contain("Invalid Json")
        }

        "given balance missing value_enc field then return bad request" in new WithServer {
          val response = await(WS.url(s"http://localhost:$port/rest/balances")
            .withHeaders("X-AUTH-TOKEN" -> googleId)
            .withHeaders("Content-Type" -> "application/json")
            .post(Json.toJson("{name_enc:\"invalid item\"}")))
          println(Json.toJson("name_enc"->"invalid item"))
          println(response.body)
          response.status must equalTo(BAD_REQUEST)
        }

        "save then return success message" in new WithServer {
          val balanceToSave = BalanceJsonSave("New Entry", "1000.00")
          val response = await(WS.url(s"http://localhost:$port/rest/balances")
            .withHeaders("X-AUTH-TOKEN" -> googleId)
            .post(Json.toJson(balanceToSave)))
          val status = response.json \ "status"
          val message = response.json \ "message"
          status.as[String] must equalTo("OK")
          message.as[String] must equalTo("Balance 'New Entry' saved.")
        }

        "then return the stored balance" in new WithServer {
          implicit val response = await(WS.url(s"http://localhost:$port/rest/balances?name_enc=New%20Entry").withHeaders("X-AUTH-TOKEN" -> googleId).get)
          implicit val balances = Json.fromJson[JsonFmtListWrapper[BalanceJson]](response.json).get
          thenOneResult[BalanceJson]{balanceList=>
            balanceList(0).name_enc must equalTo("New Entry")
            balanceList(0).value_enc must equalTo("1000.00")
            balanceList(0).id should be > -1
          }
        }
      }
    }
  }

  "/rest/stocks should" should {

    "given no x-auth-token header then return bad request" in new WithServer {
      val response = await(WS.url(s"http://localhost:$port/rest/stocks").get)
      response.status must equalTo(BAD_REQUEST)
      response.json must equalTo(Json.obj("status" -> "MH", "message" -> "missing X-AUTH-TOKEN http header"))
    }
    "given x-auth-header" should {
      "then return first two stocks sort by id asc" in new WithServer {
        implicit val response = await(WS.url(s"http://localhost:$port/rest/stocks").withHeaders("X-AUTH-TOKEN" -> googleId).get)
        implicit val stocks = Json.fromJson[JsonFmtListWrapper[StockJson]](response.json).get
        thenTwoResults[StockJson] {stockList=>
          checkFirstStock(stockList(0))
          checkSecondStock(stockList(1))
        }
      }

      "given sort by date desc then return two stocks" in new WithServer {
        implicit val response = await(WS.url(s"http://localhost:$port/rest/stocks?sort=date&direction=desc").withHeaders("X-AUTH-TOKEN" -> googleId).get)
        implicit val stocks = Json.fromJson[JsonFmtListWrapper[StockJson]](response.json).get
        thenTwoResults[StockJson] {stockList=>
          checkFirstStock(stockList(0))
          checkSecondStock(stockList(1))
        }
      }

      "given sort by date asc then return two stocks" in new WithServer {
        implicit val response = await(WS.url(s"http://localhost:$port/rest/stocks?sort=date&direction=asc").withHeaders("X-AUTH-TOKEN" -> googleId).get)
        implicit val stocks = Json.fromJson[JsonFmtListWrapper[StockJson]](response.json).get
        thenTwoResults[StockJson] {stockList=>
          checkSecondStock(stockList(0))
          checkFirstStock(stockList(1))
        }
      }

      "given items eq one then return one stock sort by id asc" in new WithServer {
        implicit val response = await(WS.url(s"http://localhost:$port/rest/stocks?items=1").withHeaders("X-AUTH-TOKEN" -> googleId).get)
        implicit val stocks = Json.fromJson[JsonFmtListWrapper[StockJson]](response.json).get
        thenOneResultTwoCounts[StockJson](stockList=>checkFirstStock(stockList(0)))
      }

      "given name eq 'Aktie 1' then return one stock" in new WithServer {
        implicit val response = await(WS.url(s"http://localhost:$port/rest/stocks?name_enc=Aktie%201").withHeaders("X-AUTH-TOKEN" -> googleId).get)
        implicit val stocks = Json.fromJson[JsonFmtListWrapper[StockJson]](response.json).get
        thenOneResult[StockJson](stockList=>checkFirstStock(stockList(0)))
      }

      "given date eq now then return one stock" in new WithServer {
        implicit val response = await(WS.url(s"http://localhost:$port/rest/stocks?date=${now.get.getTime}").withHeaders("X-AUTH-TOKEN" -> googleId).get)
        implicit val stocks = Json.fromJson[JsonFmtListWrapper[StockJson]](response.json).get
        thenOneResult[StockJson](stockList=>checkFirstStock(stockList(0)))
      }

      "save stocks" should {

        "given no x-auth-token header then return in bad request" in new WithServer {
          val balanceToSave = StockJsonSave("New Entry", "1000.00")
          val response = await(WS.url(s"http://localhost:$port/rest/stocks")
            .post(Json.toJson(balanceToSave)))
          response.status must equalTo(BAD_REQUEST)
          response.json must equalTo(Json.obj("status" -> "MH", "message" -> "missing X-AUTH-TOKEN http header"))
        }

        "given invalid json then return bad request" in new WithServer {
          val response = await(WS.url(s"http://localhost:$port/rest/stocks")
            .withHeaders("X-AUTH-TOKEN" -> googleId)
            .withHeaders("Content-Type" -> "application/json")
            .post("invalid json"))
          response.status must equalTo(BAD_REQUEST)
          response.body must contain("Invalid Json")
        }

        "given balance missing value_enc field then return bad request" in new WithServer {
          val response = await(WS.url(s"http://localhost:$port/rest/stocks")
            .withHeaders("X-AUTH-TOKEN" -> googleId)
            .withHeaders("Content-Type" -> "application/json")
            .post(Json.toJson("{name_enc:\"invalid item\"}")))
          println(Json.toJson("name_enc"->"invalid item"))
          println(response.body)
          response.status must equalTo(BAD_REQUEST)
        }

        "save then return success message" in new WithServer {
          val stockToSave = StockJsonSave("New Stock", "1002.00")
          val response = await(WS.url(s"http://localhost:$port/rest/stocks")
            .withHeaders("X-AUTH-TOKEN" -> googleId)
            .post(Json.toJson(stockToSave)))
          val status = response.json \ "status"
          val message = response.json \ "message"
          status.as[String] must equalTo("OK")
          message.as[String] must equalTo("Stock 'New Stock' saved.")
        }

        "then return the stored stock" in new WithServer {
            implicit val response = await(WS.url(s"http://localhost:$port/rest/stocks?name_enc=New%20Stock").withHeaders("X-AUTH-TOKEN" -> googleId).get)
            implicit val stocks = Json.fromJson[JsonFmtListWrapper[StockJson]](response.json).get
            thenOneResult[StockJson]{stockList=>
              stockList(0).name_enc must equalTo("New Stock")
              stockList(0).value_enc must equalTo("1002.00")
              stockList(0).id should be > -1
            }
        }
      }
    }
  }

  //then

  def thenOneResult[A](check: List[A] => Unit)(implicit response: play.api.libs.ws.Response, jsonObj: JsonFmtListWrapper[A]) {
    response.status must equalTo(OK)
    jsonObj.count must equalTo(1)
    jsonObj.items.length must equalTo(1)
    check(jsonObj.items)
  }

  def thenTwoResults[A](check: List[A] => Unit)(implicit response: play.api.libs.ws.Response, jsonObj: JsonFmtListWrapper[A]) {
    response.status must equalTo(OK)
    jsonObj.count must equalTo(2)
    jsonObj.items.length must equalTo(2)
    check(jsonObj.items)
  }

  def thenOneResultTwoCounts[A](check: List[A] => Unit)(implicit response: play.api.libs.ws.Response, jsonObj: JsonFmtListWrapper[A]) {
    response.status must equalTo(OK)
    jsonObj.count must equalTo(2)
    jsonObj.items.length must equalTo(1)
    check(jsonObj.items)
  }

  def checkFirstBalance(balance: BalanceJson) {
    balance.name_enc must equalTo("Total")
    balance.value_enc must equalTo("100.01")
    balance.date.getTime must equalTo(now.get.getTime)
  }

  def checkSecondBalance(balance: BalanceJson) {
    balance.name_enc must equalTo("Girokonto")
    balance.value_enc must equalTo("281.21")
    balance.date.getTime must equalTo(yesterday.get.getTime)
  }

  def checkFirstStock(stock: StockJson) {
    stock.name_enc must equalTo("Aktie 1")
    stock.value_enc must equalTo("11.01")
    stock.date.getTime must equalTo(now.get.getTime)
  }

  def checkSecondStock(stock: StockJson) {
    stock.name_enc must equalTo("Aktie 2")
    stock.value_enc must equalTo("28.21")
    stock.date.getTime must equalTo(yesterday.get.getTime)
  }
}