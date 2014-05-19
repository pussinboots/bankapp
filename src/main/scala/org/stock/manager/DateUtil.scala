package org.akkreditierung

import java.sql.{Date, Timestamp}
import java.util.Calendar

object DateUtil {
  def daysBeforDateTime(days: Int) : Timestamp = {
    val cal = Calendar.getInstance()
    cal.add(Calendar.DAY_OF_MONTH, - days)
    new Timestamp(cal.getTimeInMillis)
  }
  def nowDateTime() : Timestamp = new Timestamp(Calendar.getInstance().getTimeInMillis)
  def nowDateTimeOpt() : Option[Timestamp] = Some(nowDateTime())
}
