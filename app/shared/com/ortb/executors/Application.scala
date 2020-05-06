package shared.com.ortb.executors

import java.sql.Timestamp

import shared.com.ortb.manager.AppManager
import shared.com.ortb.persistent.Repositories
import shared.com.ortb.persistent.schema.Tables._
import shared.io.helpers.JodaDateTimeHelper
import shared.io.loggers.AppLogger

object Application {
  def main(args : Array[String]) : Unit = {
    val appManager = new AppManager
    println(appManager.config)

    val repos = new Repositories(appManager)

    val date = JodaDateTimeHelper.nowUtcMillis
    val dateToDob = JodaDateTimeHelper.nowUtcMillis.toDouble

    val bidResponseRepository = repos
      .bidResponseRepository

    val allRows = bidResponseRepository.getAll
    AppLogger.logEntitiesNonFuture(true, allRows, "all rows")
//    val toAllDates = allRows.map(w => {
//      w.cr2.get..toString
//    })

//    AppLogger.logEntitiesNonFuture(true, toAllDates, "toAllDates")

    val row = BidresponseRow(
      -1,
      createddatetimestamp=JodaDateTimeHelper.nowUtcJavaInstant)
    val response = bidResponseRepository
      .add(row)

    bidResponseRepository
      .add(row)

    AppLogger.debug(response.toString)
  }
}
