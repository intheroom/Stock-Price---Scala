package Main

object Main extends App {
  val json_path = "AAPL.json"
  val input_start_date = "1997-10-10"
  val input_end_date = "2018-02-10"

  // Preprocess date time format
  val date_format = new java.text.SimpleDateFormat("yyyy-MM-dd")
  val start_date = date_format.parse(input_start_date)
  val end_date = date_format.parse(input_end_date)

  val day_price_data = JSONProcessor.execute(json_path)

  val (average_opening_price, min_opening_price, max_opening_price, closing_price_moving_average,
  closing_price_linearly_weighted_moving_average) = DataProcessor.execute(day_price_data, start_date, end_date)

  printf("From date %s to date %s: ", input_start_date, input_end_date)
  printf("\nAverage opening price: %.2f", average_opening_price)
  printf("\nMin opening price: %.2f ", min_opening_price)
  printf("\nMax opening price: %.2f ", max_opening_price)
  printf("\nMoving average closing price: %s", closing_price_moving_average.mkString(", "))
  printf("\nLinearly weighted moving average closing price: %s", closing_price_linearly_weighted_moving_average.mkString(", "))

}