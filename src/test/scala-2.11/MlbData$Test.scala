import org.joda.time.DateTime
import org.scalatest.FunSuite

class MlbData$Test extends FunSuite {

  test("getAllGames returns a list of game objects for the given date") {

    // Arrange
    val date = new DateTime(2015, 4, 15, 0, 0, 0, 0)

    // Act
    val games = MlbData.getGames(date)

  }
  /*
  test("getPitcher should convert a Node object into a pitcher entity") {

    // Arrange

  } */
}
