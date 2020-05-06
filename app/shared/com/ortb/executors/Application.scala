package shared.com.ortb.executors

import shared.com.ortb.manager.AppManager
import shared.com.ortb.persistent.Repositories
import shared.com.ortb.persistent.schema.Tables._
import shared.io.helpers.JodaDateTimeHelper
import shared.io.loggers.AppLogger
import enumeratum.values._
sealed abstract class Judgement(val value: Int) extends IntEnumEntry with AllowAlias

object Judgement extends IntEnum[Judgement] {

  case object Good extends Judgement(1)
  case object OK extends Judgement(2)
  case object Meh extends Judgement(2)
  case object Bad extends Judgement(3)

  val values : IndexedSeq[Judgement] = findValues

}

object Application {
  def main(args : Array[String]) : Unit = {
    val appManager = new AppManager
    println(appManager.config)

    val x = Judgement.OK
    println(x == Judgement.Meh)

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
