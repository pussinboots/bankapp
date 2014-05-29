package model

import play.api.libs.json._
import java.sql.Timestamp
import play.api.libs.functional.syntax._
import play.api.libs.json.JsString
import play.api.libs.json.JsSuccess
import java.text.SimpleDateFormat
import java.util.Date

object JsonHelper {

  case class JsonFmtListWrapper[T](items: List[T], count: Int)

  implicit object TimestampFormatter extends Format[Timestamp] {
    def reads(s: JsValue): JsResult[Timestamp] = JsSuccess(new java.sql.Timestamp(s.as[Long]))

    def writes(timestamp: Timestamp) = JsNumber(timestamp.getTime)
  }

  implicit def listWrapperFormat[T: Format]: Format[JsonFmtListWrapper[T]] = (
    (__ \ "items").format[List[T]] and
      (__ \ "count").format[Int]
    )(JsonFmtListWrapper.apply, unlift(JsonFmtListWrapper.unapply))

  implicit val balanceJsonWrites = Json.format[BalanceJson]
  implicit val balanceJsonSaveWrites = Json.writes[BalanceJsonSave]
  implicit val balanceReads: Reads[BalanceJsonSave] = (
    (JsPath \ "name_enc").read[String] and
      (JsPath \ "value_enc").read[String]
    )(BalanceJsonSave.apply _)

  implicit def tuple2Writes[A, B](implicit aWrites: Writes[A], bWrites: Writes[B]): Writes[Tuple2[A, B]] = new Writes[Tuple2[A, B]] {
    def writes(tuple: Tuple2[A, B]) = JsArray(Seq(aWrites.writes(tuple._1), bWrites.writes(tuple._2)))
  }

  implicit val stockAndSymbolWrites = Json.format[StockJson]

  implicit val userWrites = Json.writes[UserAccount]
  implicit val userReads = Json.reads[UserAccount]
}
