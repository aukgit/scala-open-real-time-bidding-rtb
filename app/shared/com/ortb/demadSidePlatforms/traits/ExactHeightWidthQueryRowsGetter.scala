package shared.com.ortb.demadSidePlatforms.traits

import shared.com.ortb.demadSidePlatforms.DemandSidePlatformBiddingAgent
import shared.com.ortb.model.auctionbid.biddingRequests.banners.BannerImpressionModel
import shared.com.ortb.persistent.repositories.AdvertiseRepository

trait ExactHeightWidthQueryRowsGetter {
  this : DemandSidePlatformBiddingAgent =>

  def getExactHeightWidthQueryRows(
    advertiseRepository : AdvertiseRepository,
    advertisesQuery : Query[Advertise, AdvertiseRow, Seq],
    banner : BannerImpressionModel) : Option[Seq[AdvertiseRow]] = {
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
