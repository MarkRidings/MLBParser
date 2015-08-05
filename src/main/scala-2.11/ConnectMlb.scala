import org.joda.time.DateTime

import scala.xml.{NodeSeq, XML, Elem}

object ConnectMlb {

  val baseUrl = "http://gd2.mlb.com"
  val componentUrl = baseUrl + "/components/game/mlb/"
  val masterScoreboardFileName = "master_scoreboard.xml"
  val boxscoreFileName = "boxscore.xml"

  def getHomePitchers(boxscore: Elem): NodeSeq = {
    (boxscore \\ "pitching").filter(x =>
      x.attributes("team_flag").exists(i => i.text == "home")) \ "pitcher"
  }

  def getAwayPitchers(boxscore: Elem): NodeSeq = {
    (boxscore \\ "pitching").filter(x =>
      x.attributes("team_flag").exists(i => i.text == "away")) \ "pitcher"
  }

  def getHomeBatters(boxscore: Elem): NodeSeq = {
    (boxscore \\ "batting").filter(x =>
      x.attributes("team_flag").exists(i => i.text == "home")) \ "batter"
  }

  def getAwayBatters(boxscore: Elem): NodeSeq = {
    (boxscore \\ "batting").filter(x =>
      x.attributes("team_flag").exists(i => i.text == "away")) \ "batter"
  }

  def getGames(date: DateTime):List[String] = {
    val doc = getMasterScoreboardXml(date)
    val gameNodes = doc \\ "game"

    gameNodes.map(x => (x \ "@game_data_directory").text).toList

  }

  def getMasterScoreboardXml(date: DateTime):Elem = {
    val url = createUrlFromDate(date) + masterScoreboardFileName

    XML.load(url)
  }

  def getBoxscoreXml(gamesDirectory: String):Elem = {

    XML.load(baseUrl + gamesDirectory + "/" + boxscoreFileName)
  }

  def createUrlFromDate(date: DateTime): String = {
    val url = componentUrl +
      "year_" + getYear(date) +
      "/month_" + getMonth(date) +
      "/day_" + getDay(date) + "/"

    url
  }

  def getYear(date: DateTime): String = {
    date.getYear.toString
  }

  def getMonth(date: DateTime): String = {
    if (date.getMonthOfYear > 9) {
      date.getMonthOfYear.toString
    }
    else {
      "0" + date.getMonthOfYear
    }
  }

  def getDay(date: DateTime): String = {
    if (date.getDayOfMonth > 9) {
      date.getDayOfMonth.toString
    }
    else {
      "0" + date.getDayOfMonth
    }
  }

}
