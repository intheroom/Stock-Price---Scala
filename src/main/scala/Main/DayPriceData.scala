package Main

import play.api.libs.json.{JsPath, Reads}
import play.api.libs.functional.syntax._

class DayPriceData(var date: java.util.Date, var open: Double, var close: Double) {
  override def toString: String = {
    "Date: " + date.toString + " Open Price: " + open.toString + " Close Price: " + close.toString
  }

  def getDate: java.util.Date = date
  def getOpeningPrice: Double = open
  def getClosingPrice: Double = close

}

object DayPriceData {
  def apply(date: String, open: Double, close: Double): DayPriceData = {
    val format = new java.text.SimpleDateFormat("yyyy-MM-dd")
    new DayPriceData(format.parse(date), open, close)
  }

  implicit def DayPriceDataReads(column_names:List[String]): Reads[DayPriceData] = (
    (JsPath \ column_names.indexOf("Date")).read[String] and
      (JsPath \ column_names.indexOf("Open")).read[Double] and
      (JsPath \ column_names.indexOf("Close")).read[Double]
    ) (DayPriceData.apply _)
}