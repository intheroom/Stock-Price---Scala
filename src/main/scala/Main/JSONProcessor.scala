package Main

import java.io.FileNotFoundException

import play.api.libs.json.{JsValue, Json}
import DayPriceData._

object JSONProcessor {
  def execute(json_path: String): List[DayPriceData] = {
    val json_content = readStringFromPath(json_path)
    val json_data = getJsonValueFromString(json_content)
    val dataset = json_data("dataset")
    val column_names = dataset("column_names").as[List[String]]
    val data = dataset("data")
    implicit val read_implicit = DayPriceDataReads(column_names)

    data.as[List[DayPriceData]]
  }

  def readStringFromPath(json_path: String): String = {
    val file = scala.io.Source.fromFile(json_path)
    val res = file.mkString
    file.close()
    res
  }

  def getJsonValueFromString(json_content: String): JsValue = {
    Json.parse(json_content)
  }
}
