import org.joda.time.DateTime
import org.scalatest.FunSuite

import scala.xml.NodeSeq

class ConnectMlb$Test extends FunSuite {

  test("CreateUrlFromDate should create valid url for a given date") {

    // Arrange
    val date1 = new DateTime(2015, 4, 26, 0, 0, 0, 0)
    val date2 = new DateTime(2015, 4, 5, 0, 0, 0, 0)
    val date3 = new DateTime(2015, 10, 10, 0, 0, 0, 0)

    // Act
    val url1 = ConnectMlb.createUrlFromDate(date1)
    val url2 = ConnectMlb.createUrlFromDate(date2)
    val url3 = ConnectMlb.createUrlFromDate(date3)

    // Assert
    assert(url1 == "http://gd2.mlb.com/components/game/mlb/year_2015/month_04/day_26/")
    assert(url2 == "http://gd2.mlb.com/components/game/mlb/year_2015/month_04/day_05/")
    assert(url3 == "http://gd2.mlb.com/components/game/mlb/year_2015/month_10/day_10/")
  }

  test("getMasterScoreboardXml should return a masterscoreboard.xml for the correct date") {

    // Arrange
    val date = new DateTime(2015, 4, 15, 0, 0, 0, 0)

    // Act
    val doc = ConnectMlb.getMasterScoreboardXml(date)
    val games: NodeSeq = doc \\ "game"

    // Assert
    assert(games.length == 15)
    assert((games(0) \ "@id").text == "2015/04/15/chamlb-clemlb-1")
  }

  test("getGames should return a list of gamedirectories for a given date") {

    // Arrange
    val date = new DateTime(2015, 4, 15, 0, 0, 0, 0)

    // Act
    val games = ConnectMlb.getGames(date)

    // Assert
    assert(games.length == 15)
    assert(games.head == "/components/game/mlb/year_2015/month_04/day_15/gid_2015_04_15_chamlb_clemlb_1")
    assert(games(3) == "/components/game/mlb/year_2015/month_04/day_15/gid_2015_04_15_anamlb_texmlb_1")
  }

  test("getBoxscore should return a boxscore.xml file for the given game directory") {

    // Arrange
    val date = new DateTime(2015, 4, 15, 0, 0, 0, 0)

    // Act
    val gameList = ConnectMlb.getGames(date)
    val boxscore = ConnectMlb.getBoxscoreXml(gameList(3))
    val homePitchers = (boxscore \\ "pitching").filter(x =>
                          x.attributes("team_flag").exists(i => i.text == "home")) \ "pitcher"
    val awayPitchers = (boxscore \\ "pitching").filter(x =>
                          x.attributes("team_flag").exists(i => i.text == "away")) \ "pitcher"

    // Assert
    assert(homePitchers.length == 4)
    assert((homePitchers.head \ "@name").text == "Ranaudo")
    assert(awayPitchers.length == 2)

  }

  test("getHomePitchers should return a list of NodeObjects of the pitchers from the home team") {

    // Arrange
    val date = new DateTime(2015, 4, 15, 0, 0, 0, 0)
    val gameList = ConnectMlb.getGames(date)
    val boxscore = ConnectMlb.getBoxscoreXml(gameList(3))

    // Act
    val homePitchers = ConnectMlb.getHomePitchers(boxscore)

    // Assert
    assert(homePitchers.length == 4)
    assert((homePitchers(1) \ "@name").text == "Bass")
  }

  test("getAwayPitchers should return a list of Node Objects of the pitchers from the away team") {

    // Arrange
    val date = new DateTime(2015, 4, 15, 0, 0, 0, 0)
    val gameList = ConnectMlb.getGames(date)
    val boxscore = ConnectMlb.getBoxscoreXml(gameList(3))

    // Act
    val awayPitchers = ConnectMlb.getAwayPitchers(boxscore)

    // Assert
    assert(awayPitchers.length == 2)
    assert((awayPitchers(0) \ "@name").text == "Santiago, H")

  }

  test("getHomeBatters should return a list of Node objects of the batters from the home team") {

    // Arrange
    val date = new DateTime(2015, 4, 15, 0, 0, 0, 0)
    val gameList = ConnectMlb.getGames(date)
    val boxscore = ConnectMlb.getBoxscoreXml(gameList(3))

    // Act
    val homeBatters = ConnectMlb.getHomeBatters(boxscore)

    // Assert
    assert(homeBatters.length == 15)
    assert((homeBatters(2) \ "@name").text == "Beltre, A")
  }

  test("getAwayBatters should return a list of Node Objects of the batters from the away team") {

    // Arrange
    val date = new DateTime(2015, 4, 15, 0, 0, 0, 0)
    val gameList = ConnectMlb.getGames(date)
    val boxscore = ConnectMlb.getBoxscoreXml(gameList(3))

    // Act
    val awayBatters = ConnectMlb.getAwayBatters(boxscore)

    // Assert
    assert(awayBatters.length == 12)
    assert((awayBatters(2) \ "@name").text == "Pujols")
  }
}
