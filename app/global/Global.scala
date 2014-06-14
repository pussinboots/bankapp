package global

import play.api._
import play.api.mvc._
import scala.concurrent.Future
import model.DB

object Global extends WithFilters(HTTPSRedirectFilter) with GlobalSettings
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
      Logger.info("Application has started")
    }
    override def onStop(app: Application) {
      Logger.info("Application shutdown...")
    }
}

object HTTPSRedirectFilter extends Filter {

  def apply(nextFilter: (RequestHeader) => Future[SimpleResult])(requestHeader: RequestHeader): Future[SimpleResult] = {
    //play uses lower case headers.
    implicit val context = scala.concurrent.ExecutionContext.Implicits.global
    requestHeader.headers.get("x-forwarded-proto") match {
      case Some(header) => {
        if ("https" == header) {
          nextFilter(requestHeader).map { result =>
            result.withHeaders(("Strict-Transport-Security", "max-age=31536000"))
          }
        } else {
          Future.successful(Results.Redirect("https://" + requestHeader.host + requestHeader.uri, 301))
        }
      }
      case None => nextFilter(requestHeader)
    }
  }
}
