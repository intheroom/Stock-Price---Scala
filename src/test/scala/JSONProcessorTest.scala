import Main.JSONProcessor._
import org.scalatest.FlatSpec

class JSONProcessorTest extends FlatSpec {
  behavior of "A JSONProcessor"

  it should "throw a FileNotFoundException if the file is not found" in {
    intercept[java.io.FileNotFoundException] {
      readStringFromPath("1.json")
    }
  }

  it should "throw a JsonParseException if the file doesn't contain JSON" in {
    intercept[com.fasterxml.jackson.core.JsonParseException] {
      getJsonValueFromString("asdfads")
    }
  }

  it should "throw a NoSuchElementException if the JSON is not correctly formatted" in {
    intercept[java.util.NoSuchElementException] {
      val json_content = readStringFromPath("1.txt")
      val json_data = getJsonValueFromString(json_content)
      json_data("dataset")
    }
  }

}