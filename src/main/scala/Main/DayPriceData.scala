package Main

import play.api.libs.json.{JsPath, Reads}
import play.api.libs.functional.syntax._

case class DayPriceData(var date: String,
                        var open: Double,
                        var close: Double) {
  val m_format = new java.text.SimpleDateFormat("yyyy-MM-dd")
  var m_date: java.util.Date = m_format.parse(date)
  var m_open: Double = open
  var m_close: Double = close

  override def toString: String = {
    "Date: " + m_date.toString + " Open Price: " + m_open.toString + " Close Price: " + m_close.toString
  }
}

object Implicit {
  implicit def DayPriceDataReads(column_names:List[String]): Reads[DayPriceData] = (
      (JsPath \ column_names.indexOf("Date")).read[String] and
      (JsPath \ column_names.indexOf("Open")).read[Double] and
      (JsPath \ column_names.indexOf("Close")).read[Double]
    ) (DayPriceData.apply _)
}