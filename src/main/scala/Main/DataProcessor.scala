package Main

object DataProcessor {
  def execute(data: List[DayPriceData], start_date: java.util.Date, end_date: java.util.Date): (Double, Double, Double, List[Double], List[Double]) = {
    val filtered_data = filterData(data, start_date, end_date)
    val average_opening_price = computeOpeningPriceAvg(filtered_data)
    val min_opening_price = computeOpeningPriceMin(filtered_data)
    val max_opening_price = computeOpeningPriceMax(filtered_data)
    val closing_price_moving_average = computeClosingPriceMovingAverage(filtered_data, 50)
    val closing_price_linearly_weighted_moving_average = computeClosingPriceLinearlyMovingAverage(filtered_data, 50)

    (average_opening_price, min_opening_price, max_opening_price,
      closing_price_moving_average, closing_price_linearly_weighted_moving_average)
  }

  def filterData(data: List[DayPriceData], start_date: java.util.Date, end_date: java.util.Date): List[DayPriceData] = {
    data.filter(x => x.m_date.compareTo(start_date) >= 0 && x.m_date.compareTo(end_date) < 0)
  }

  def computeOpeningPriceAvg(data: List[DayPriceData]): Double = {
    data.map(_.m_open).sum / data.length
  }

  def computeOpeningPriceMin(data: List[DayPriceData]): Double = {
    data.map(_.m_open).min
  }

  def computeOpeningPriceMax(data: List[DayPriceData]): Double = {
    data.map(_.m_open).max
  }

  def computeClosingPriceMovingAverage(data: List[DayPriceData], window_size: Int): List[Double] = {
    data.map(_.m_close).sliding(window_size).map(_.sum / window_size).toList
  }

  def computeClosingPriceLinearlyMovingAverage(data: List[DayPriceData], window_size: Int): List[Double] = {
    data.map(_.m_close).sliding(window_size).map(x => x.zipWithIndex)
      .map(x => x.foldLeft((0.0, 0))
      { (res: (Double, Int), elem: (Double, Int)) => (res._1 + elem._1 * (elem._2 + 1), res._2 + (elem._2 + 1))} )
      .map(x => x._1 / x._2)
      .toList
  }
}
