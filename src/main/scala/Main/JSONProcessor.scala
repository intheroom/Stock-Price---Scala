package Main

import play.api.libs.json.Json
import Implicit._


object JSONProcessor {
  def execute(json_path: String): List[DayPriceData] = {
    val json_content = scala.io.Source.fromFile(json_path).mkString
    val json_data = Json.parse(json_content)
    val dataset = json_data("dataset")
    val column_names = dataset("column_names").as[List[String]]
    val data = dataset("data")
    implicit val read_implicit = DayPriceDataReads(column_names)

    data.as[List[DayPriceData]]
  }
}
