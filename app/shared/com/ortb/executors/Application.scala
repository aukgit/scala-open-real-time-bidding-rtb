package shared.com.ortb.executors

import io.circe.generic.semiauto._
import io.circe._
import io.circe.parser._
import io.circe.generic.auto._
import io.circe.generic.encoding.DerivedAsObjectEncoder
import io.circe.generic.encoding._
import io.circe.{ Decoder, Encoder }, io.circe.generic.semiauto._
import io.circe.{Decoder, Encoder}
import io.circe.generic.semiauto._
import io.circe.syntax._
import io.circe.Decoder.AccumulatingResult
import io.circe.generic.JsonCodec
import io.circe.derivation._
import io.circe.optics.JsonPath._
import io.circe.optics._
import shared.com.ortb.manager.AppManager
import shared.com.ortb.persistent.Repositories
import shared.com.ortb.persistent.schema.Tables._
import shared.io.helpers.JodaDateTimeHelper
import shared.io.loggers.AppLogger
import enumeratum.values._
sealed abstract class Judgement(val value: Int) extends IntEnumEntry with AllowAlias
sealed abstract class LibraryItem(val value: Int, val name: String) extends IntEnumEntry
sealed abstract class CirceLibraryItem(val value: Int, val name: String) extends IntEnumEntry
case object CirceLibraryItem extends IntEnum[CirceLibraryItem] with IntCirceEnum[CirceLibraryItem] {

  // A good mix of named, unnamed, named + unordered args
  case object Book     extends CirceLibraryItem(value = 1, name = "book")
  case object Movie    extends CirceLibraryItem(name = "movie", value = 2)
  case object Magazine extends CirceLibraryItem(3, "magazine")
  case object CD       extends CirceLibraryItem(4, name = "cd")

  val values = findValues

}

import io.circe.Json
import io.circe.syntax._
object LibraryItem extends IntEnum[LibraryItem] {


  case object Book     extends LibraryItem(value = 1, name = "book")
  case object Movie    extends LibraryItem(name = "movie", value = 2)
  case object Magazine extends LibraryItem(3, "magazine")
  case object CD       extends LibraryItem(4, name = "cd")
  // case object Newspaper extends LibraryItem(4, name = "newspaper") <-- will fail to compile because the value 4 is shared

  /*
  val five = 5
  case object Article extends LibraryItem(five, name = "article") <-- will fail to compile because the value is not a literal
  */

  val values = findValues

}

object Judgement extends IntEnum[Judgement] {

  case object Good extends Judgement(1)
  case object OK extends Judgement(2)
  case object Meh extends Judgement(2)
  case object Bad extends Judgement(3)

  val values : IndexedSeq[Judgement] = findValues
}

case class MX(
  w : Judgement,
  w2: Judgement,
  w3 : LibraryItem,
  w4: CirceLibraryItem)

object Application {
  def main(args : Array[String]) : Unit = {
    val appManager = new AppManager
    println(appManager.config)

    val x = Judgement.OK
    println(x == Judgement.Meh)
    print(MX(
      Judgement.withValue(1),
      Judgement.withValue(2),
      LibraryItem.withValue(2),
      CirceLibraryItem.Book).asJson)

    val jsonString = """
      |{
      |  "w" : {
      |    "Good" : {}
      |  },
      |  "w2" : {
      |    "OK" : {
      |
      |    }
      |  },
      |  "w3" : {
      |    "Book" : {
      |
      |    }
      |  }
      |}
      |""".stripMargin

    val mx2 = decode[MX](jsonString)
    println(mx2.getOrElse(null))


    println(x == Judgement.OK)

    val repos = new Repositories(appManager)
    val campaignsResponse = repos.campaignRepository.getAllAsResponse
    campaignsResponse.logResults()

    val bidResponseRepository = repos
      .bidResponseRepository
    // println(nameOf(bidResponseRepository))

    val allRows = bidResponseRepository.getAll
    AppLogger.logEntities( allRows, "all rows")
    //    val toAllDates = allRows.map(w => {
    //      w.cr2.get..toString
    //    })

    //    AppLogger.logEntitiesNonFuture(true, toAllDates, "toAllDates")

    val row = BidresponseRow(
      -1,
      createddatetimestamp = JodaDateTimeHelper.nowUtcJavaInstant)
    val response = bidResponseRepository
      .add(row)

    bidResponseRepository
      .add(row)

    AppLogger.debug(response.toString)
  }
}
