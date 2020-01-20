import Main.DayPriceData
import Main.DataProcessor
import org.scalatest.FlatSpec

class DataProcessorTest extends FlatSpec {
  "A data filtering" should "Filter data from the beginning of the start date to the end of the end date" in {
    val dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd")
    val startDate = dateFormat.parse("2018-01-02")
    val endDate = dateFormat.parse("2018-03-04")
    val list = List[DayPriceData](DayPriceData("2018-02-01", 10, 5), DayPriceData("2018-03-04", 1, 1), DayPriceData("2018-06-09", 2, 4))
    val res = List[DayPriceData](DayPriceData("2018-02-01", 10, 5), DayPriceData("2018-03-04", 1, 1))
    val filtered_list = DataProcessor.filterData(list, startDate, endDate)
    assert(res.head.getDate.compareTo(filtered_list.head.getDate) == 0)
    assert(res.head.getOpeningPrice == filtered_list.head.getOpeningPrice)
    assert(res.head.getClosingPrice == filtered_list.head.getClosingPrice)
    assert(res(1).getDate.compareTo(filtered_list(1).getDate) == 0)
    assert(res(1).getOpeningPrice == filtered_list(1).getOpeningPrice)
    assert(res(1).getClosingPrice == filtered_list(1).getClosingPrice)
  }

  "Average opening price computation" should "be correct" in {
    val list = List[DayPriceData](DayPriceData("2018-02-01", 10, 5), DayPriceData("2018-03-04", 1, 1), DayPriceData("2018-06-09", 2, 4))
    val res = (10 + 1 + 2) * 1.0 / 3
    assert(DataProcessor.computeOpeningPriceAvg(list) == res)
  }

  "Min opening price computation" should "be correct" in {
    val list = List[DayPriceData](DayPriceData("2018-02-01", 10, 5), DayPriceData("2018-03-04", 1, 1), DayPriceData("2018-06-09", 2, 4))
    val res = math.min(math.min(10, 1), 2) * 1.0
    assert(DataProcessor.computeOpeningPriceMin(list) == res)
  }

  "Max opening price computation" should "be correct" in {
    val list = List[DayPriceData](DayPriceData("2018-02-01", 10, 5), DayPriceData("2018-03-04", 1, 1), DayPriceData("2018-06-09", 2, 4))
    val res = math.max(math.max(10, 1), 2) * 1.0
    assert(DataProcessor.computeOpeningPriceMax(list) == res)
  }

  "Moving closing price computation" should "be correct" in {
    val list = List[DayPriceData](DayPriceData("2018-02-01", 10, 5), DayPriceData("2018-03-04", 1, 1), DayPriceData("2018-06-09", 2, 4))
    val res = List((5 + 1) * 1.0 / 2, (1 + 4) * 1.0 / 2)
    assert(DataProcessor.computeClosingPriceMovingAverage(list, 2) == res)
  }

  "Linearly moving closing price computation" should "be correct" in {
    val list = List[DayPriceData](DayPriceData("2018-02-01", 10, 5), DayPriceData("2018-03-04", 1, 1), DayPriceData("2018-06-09", 2, 4))
    val res = List((5 * 1 + 1 * 2) * 1.0 / (1 + 2), (1 * 1 + 4 * 2) * 1.0 / (1 + 2))
    assert(DataProcessor.computeClosingPriceLinearlyMovingAverage(list, 2) == res)
  }
}
