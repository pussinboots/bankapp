package unit.org.stock.manager

import org.specs2.mutable.Specification
import play.api.test._
import play.api.test.Helpers._
import play.api.libs.ws._

class UiSpec extends PlaySpecification /*with SlickDbBefore*/ {
  sequential

	"run in a server" in new WithServer {
		await(WS.url("http://localhost:" + port).get).status must equalTo(OK)
	}

	"run in a browser" in new WithBrowser {
	  browser.goTo("/")
	  /*browser.$("#title").getTexts().get(0) must equalTo("Hello Guest")
	    
	  browser.$("a").click()
	  */
	  browser.url must equalTo("/products.html")
	  //browser.$("#title").getTexts().get(0) must equalTo("Hello Coco")
	}
}
