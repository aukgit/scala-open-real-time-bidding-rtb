package shared.com.ortb.demadSidePlatforms.traits.getters

import shared.com.ortb.demadSidePlatforms.DemandSidePlatformBiddingAgent
import shared.com.ortb.model.auctionbid.biddingRequests.banners.BannerImpressionModel
import shared.com.ortb.persistent.repositories.AdvertiseRepository
import shared.com.ortb.persistent.schema.Tables._
import shared.io.helpers.EmptyValidateHelper
import slick.jdbc.SQLiteProfile.api._

trait ExactHeightWidthQueryRowsGetter {
  this : DemandSidePlatformBiddingAgent =>

  def getExactHeightWidthQueryRows(
    advertiseRepository : AdvertiseRepository,
    advertisesQuery : Query[Advertise, AdvertiseRow, Seq],
    banner : BannerImpressionModel) : Option[Seq[AdvertiseRow]] = {
    var query = advertisesQuery
    val simpleBanner = bannerModelAdapter.getSimpleBanner(banner)

    if (EmptyValidateHelper.isDefinedIntPlusPositive(banner.h)) {
      query = query.filter(w => w.height === simpleBanner.h)
    }

    if (EmptyValidateHelper.isDefinedIntPlusPositive(banner.w)) {
      query = query.filter(w => w.width === simpleBanner.w)
    }

    val results = advertiseRepository.run(query.result)

    Some(results)
  }
}
