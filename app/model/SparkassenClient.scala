package model

import dispatch.Defaults.executor
import dispatch.Http
import dispatch.enrichFuture
import dispatch.implyRequestVerbs
import dispatch.url
import org.htmlcleaner.{DoctypeToken, TagNode, HtmlCleaner}
import com.ning.http.client.Response
import java.util.{Collections, List}
import scala.collection.mutable

class DoubleHelper(str: String) {
  def toSafeDouble() =  {
    val value = str.split(" ")(0).replaceAll("\\.", "").replaceAll(",", "\\.") match { case ""|null => "0" case s => s }
    value.toDouble
  }
}

case class User(username: String, password: String)
object User {
  def fromProperties = User(sys.env.get("sparkasse_username").get, sys.env.get("sparkasse_password").get)
}
case class Form(accounts: Map[String, Account], inputs: Seq[TagNode], formUrl: String)
case class Account(name: String, number: Long, value: Double, operations: Map[String, TagNode])

class SparKassenClient() {
  implicit def stringWrapper(string: String) = new DoubleHelper(string)

  val debug = true
  val rootUrl = "https://banking.berliner-sparkasse.de"
  val cookieStore: mutable.Map[String, String] = mutable.Map[String, String]()


  def parseOverview(html: String) = {
    def nameFunc(e:TagNode) = e.getElementsByName("td", false).map(e =>e.getText.toString.trim)
    def dataFunc(data:Seq[String], actions:Map[String, TagNode]) = (normalizeName(data(0)) -> Account(normalizeName(data(0)), data(1).toLong, data(2).toSafeDouble, actions))
    parseInt(html, "tablerowevenNew", "name", nameFunc, dataFunc)
  }

  def parseStockOverview(html: String) = {
    def nameFunc(e:TagNode) = e.getElementsByName("td", true).filter(e=>Option(e.getAttributes().get("class")).getOrElse("").endsWith("tabledata")).map(e =>e.getText.toString.trim)
    def dataFunc(data:Seq[String], actions:Map[String, TagNode]) = {
      debug("data \n")
      debug(data)
      debug(data(4).toSafeDouble)
      (normalizeName2(data(1)) -> Account(normalizeName2(data(1)), 1.toLong, data(4).toSafeDouble, actions))
    }
    parseInt(html, "tableroweven", "name", nameFunc, dataFunc)
  }

  def parseInt(html: String, rowCss: String = "tablerowevenNew", htmlAttribute: String = "name", nameFunc: (TagNode)=>Seq[String], dataFunc: (Seq[String], Map[String, TagNode])=> (String, Account) ) = {
    val cleaner = new HtmlCleaner
    val props = cleaner.getProperties
    val rootNode = cleaner.clean(html)
    /*debug("html \n")
    debug(html)*/
    val elements = rootNode.getElementsByName("tr", true).filter(e =>Option(e.getAttributes().get("class")).getOrElse("").endsWith(rowCss))
      .map{elements =>
      val data = nameFunc(elements) //elements.getElementsByName("td", false).map(e =>e.getText.toString.trim)
      val actions = elements.getElementsByName("td", true).flatMap(e =>e.getElementsByName("input", true).filter(e=>e.getAttributes.containsKey(htmlAttribute)).map(e=>(e.getAttributes.get("alt"), e))).toMap
      dataFunc(data,actions)
    }
    val hiddenFields = rootNode.getElementsByName("form", true)
      .filter(e =>Option(e.getAttributes().get("action")).getOrElse("").startsWith("/portal/portal") && e.getAttributes().get("class") == null)
      .flatMap(e=>e.getElementsByName("input", false))
    val formUrl = rootNode.getElementsByName("form", true)
      .filter(e =>Option(e.getAttributes().get("action")).getOrElse("").startsWith("/portal/portal") && e.getAttributes().get("class") == null)
      .flatMap(e=>e.getAttributes.get("action")).mkString("")
    val e = elements.map(x => (x._1, x._2)).toMap
    Form(e, hiddenFields, rootUrl + formUrl)
  }
  def normalizeName2(name: String) = if (name.contains("\n")) name.substring(0, name.indexOf("\n")-1) else name
  def normalizeName(name: String) = if (name.contains("\n")) name.substring(0, name.indexOf("\n")) else name

  def login(user: User): String = {
    //val post = Map("tid" -> "80520", "reuseresult" -> "false", "stylesheet" -> "tabelle_html_akkr.xsl", "Bezugstyp" -> bezugsTyp, "sort" -> "2", "offset" -> offset, "maxoffset" -> maxOffset, "contenttype" -> "")
    val uri = url("https://banking.berliner-sparkasse.de/portal/portal")
    //val header = Map("Content-Type" -> "application/x-www-form-urlencoded", "Cookie" -> s"JSESSIONID=${sessionId}")
    val response = Http(uri / "Starten")
    val r = response()
    import collection.JavaConversions._
    val cookies: Seq[String] = r.getHeaders("Set-Cookie")
    cookies.foreach {c=>
      cookieStore.put(c.split("=")(0), c.split("=")(1).split(";")(0))
    }
    debug("cookie store " + cookieStore)
    //debug(r.getResponseBody)
    val cleaner = new HtmlCleaner
    val props = cleaner.getProperties
    val rootNode = cleaner.clean(r.getResponseBody)
    //TODO simplify parsing the html data out into the Studiengang pojo
    val elements = rootNode.getElementsByName("form", true).filter {
      e =>
        Option(e.getAttributes().get("action")).getOrElse("").endsWith("Login")
    }
    for (elem <- elements) {
      debug(elem)
      debug(elem.getAttributes)
      val formUri = url(rootUrl + elem.getAttributes.get("action"))
      val childs: Seq[TagNode] = elem.getElementsByName("input", true)
      childs.foreach(e => debug(e.getAttributes))
      println()
      childs.filter(e => e.getAttributes.containsKey("value"))
        .foreach(e => debug(e.getAttributes))
      println()
      //fertige werte
      val inputs = childs.filter(e => e.getAttributes.containsKey("value"))
        .filter(e => e.getAttributes.get("value").length > 0)
        .map(e => e.getAttributes.get("name") -> e.getAttributes.get("value"))
      println()

      val userInputs = childs.filter(e => e.getAttributes.containsKey("maxlength"))
        .filter(e => e.getAttributes.containsKey("value"))
        .filter(e => e.getAttributes.get("value").length <= 0)
        .map {
        e =>
          if (e.getAttributes.get("type") == "text") {
            e.getAttributes.get("name") -> user.username
          } else {
            e.getAttributes.get("name") -> user.password
          }
      }
      debug(inputs)
      debug(userInputs)
      val post = inputs ++ userInputs
      debug(post)
      val header = Map("Content-Type" -> "application/x-www-form-urlencoded", "Cookie" -> cookieStore.map(pair=>pair._1+"="+pair._2).mkString(";"))
      debug(header)
      debug(formUri.build().getUrl)
      val req = formUri << post <:< header
      debug(req.build())
      val response = Http(req)
      return followingRedirects(response()).getResponseBody
    }
    return ""
  }

  def stockOverview(form: Form) = {
    openOperation(form, "Wertpapierdepot", "Depotaufstellung")
  }

  def kontoDetails(form: Form, accountName: String) = {
    openOperation(form, accountName, "Kontodetails")
  }

  def openOperation(form: Form, accountName: String, operation: String): String = {
      val header = Map("Content-Type" -> "application/x-www-form-urlencoded", "Cookie" -> cookieStore.map(pair=>pair._1+"="+pair._2).mkString(";"))
      debug(header)
      val op = form.accounts.get(accountName).get.operations.get(operation).get
      val post = form.inputs.map(e=>(e.getAttributes.get("name") -> e.getAttributes.get("value"))) ++ Seq(op.getAttributes.get("name") -> op.getAttributes.get("value"))
      val req = url(form.formUrl) << post <:< header
      debug(req.build())
      val response = Http(req)
      return followingRedirects(response()).getResponseBody
  }

  def followingRedirects(r: Response) = {
    var r0 = r
    do {
      r0 = followRedirect(r0)
      debug(r0.getHeaders)
    } while (r0.getStatusCode == 302 && r0.getHeader("Location") != null)
    r0
  }
                                                                                    
  def followRedirect(response: Response) = {
    import collection.JavaConversions._
    Option[List[String]](response.getHeaders("Set-Cookie")).getOrElse(Collections.emptyList()).foreach {c=>
      cookieStore.put(c.split("=")(0), c.split("=")(1).split(";")(0))
    }
    val header = Map("Cookie" -> cookieStore.map(pair=>pair._1+"="+pair._2).mkString(";"))
    val uri = url(response.getHeader("Location")) <:< header
    debug(uri.build())
    val res = Http(uri)
    res()
  }

  def debug(message: Any) {
    if (debug)
      println(message)
  }
}
