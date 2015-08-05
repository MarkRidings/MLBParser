package Entities

import org.joda.time.DateTime

class Game {

  var id: String = ""
  var date: DateTime = new DateTime()
  var Venue: String = ""
  var VenueId: String = ""
  var AwayNameAbbrev: String = ""
  var HomeNameAbbrev: String = ""
  var AwayTeamId: String = ""
  var HomeTeamId: String = ""
  var AwayTeamCity: String = ""
  var HomeTeamCity: String = ""
  var AwayTeamName: String = ""
  var HomeTeamName: String = ""
  var AwayLeagueId: String = ""
  var HomeLeagueId: String = ""
  var GameDataDirectory: String = ""
  var AwayRuns: Int = 0
  var HomeRuns: Int = 0
  var WinningPitcherId: String = ""
  var WinningPitcherFirstName = ""
  var WinningPitcherLastName = ""
  var LosingPitcherId: String = ""
  var LosingPitcherFirstName = ""
  var LosingPitcherLastName = ""
  var SavePitcherId: String = ""
  var SavePitcherFirstName: String = ""
  var SavePitcherLastName: String = ""

  var Pitchers: List[Pitcher] = List[Pitcher]()
  var Batters: List[Batter] = List[Batter]()

}
