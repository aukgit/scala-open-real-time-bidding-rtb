package shared.com.ortb.demadSidePlatforms

import shared.com.ortb.importedModels.biddingRequests.BannerModel
import shared.com.ortb.persistent.repositories.AdvertiseRepository
import shared.com.ortb.persistent.schema.Tables._
import slick.jdbc.SQLiteProfile.api._

trait ExactHeightWidthQueryRowsGetter {
  this : DemandSidePlatformBiddingAgent =>

  def getExactHeightWidthQueryRows(
    advertiseRepository : AdvertiseRepository,
    advertisesQuery : Query[Advertise, AdvertiseRow, Seq],
    banner : BannerModel) : Option[Seq[AdvertiseRow]] = {
    var query = advertisesQuery
    val simpleBanner = bannerModelAdapter.getSimpleBanner(banner)

    if (banner.h.isDefined) {
      query = query.filter(w => w.height === simpleBanner.h)
    }

    if (banner.w.isDefined) {
      query = query.filter(w => w.width === simpleBanner.w)
    }

    val results = advertiseRepository.run(query.result)

    Some(results)
  }
}
