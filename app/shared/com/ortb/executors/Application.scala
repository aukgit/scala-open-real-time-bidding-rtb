package shared.com.ortb.executors

import com.github.dwickern.macros.NameOf._
import shared.com.ortb.manager.AppManager
import shared.com.ortb.persistent.Repositories
import shared.com.ortb.persistent.schema.Tables._
import shared.io.extensions.TypeConvertExtensions._
import shared.io.helpers.JodaDateTimeHelper
import shared.io.loggers.AppLogger

object Application {
  def main(args : Array[String]) : Unit = {
    println("Start")
    val appManager = new AppManager
    println(appManager.config)
    println("Manager Complete")
    val repos = new Repositories(appManager)
    val campaignsResponse = repos.campaignRepository.getAllAsResponse

    val json = campaignsResponse.rows.toPrettyJsonString
    println(json)

    campaignsResponse.rows.logToDatabaseAsJson(nameOf(main _))

    val bidResponseRepository = repos
      .bidResponseRepository

    val allRows = bidResponseRepository.getAll
    allRows.logToDatabaseAsJson("main")
    AppLogger.logEntities(allRows, "all rows")
    val row = BidresponseRow(
      -1,
      createddatetimestamp = JodaDateTimeHelper.nowUtcJavaInstant)

    val response = bidResponseRepository
      .add(row)

    bidResponseRepository
      .add(row)

    bidResponseRepository.addEntities(Seq(row, row))
    val res = response.data.get

    AppLogger.debug(res.toString)
  }

  /**
   *
   * val repos = new Repositories(appManager)
   * val campaignsResponse = repos.campaignRepository.getAllAsResponse
   *campaignsResponse.logResults()
   * *
   * val bidResponseRepository = repos
   * .bidResponseRepository
   * println(nameOf(bidResponseRepository))
   * *
   * val allRows = bidResponseRepository.getAll
   *AppLogger.logEntities( allRows, "all rows")
   */
}
