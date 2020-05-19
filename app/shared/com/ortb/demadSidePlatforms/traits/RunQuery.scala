package shared.com.ortb.demadSidePlatforms.traits

import shared.com.ortb.demadSidePlatforms.DemandSidePlatformBiddingAgent
import shared.com.ortb.persistent.schema.Tables._
import slick.jdbc.SQLiteProfile.api._

trait RunQuery {
  this : DemandSidePlatformBiddingAgent =>

  def runQuery(
    query : Query[Advertise, AdvertiseRow, Seq]) : Option[Seq[AdvertiseRow]] = {
    val advertiseRepository = coreProperties
      .repositories
      .advertiseRepository

    val results = advertiseRepository
      .run(query.result)

    Some(results)
  }
}
