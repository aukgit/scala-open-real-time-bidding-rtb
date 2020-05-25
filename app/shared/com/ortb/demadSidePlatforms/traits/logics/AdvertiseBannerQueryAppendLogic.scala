package shared.com.ortb.demadSidePlatforms.traits.logics

import shared.com.ortb.constants.AppConstants
import shared.com.ortb.model.auctionbid.biddingRequests.banners.BannerImpressionModel
import shared.com.ortb.persistent.schema.Tables._
import shared.io.helpers.EmptyValidateHelper
import slick.jdbc.SQLiteProfile.api._
import slick.lifted.Query

trait AdvertiseBannerQueryAppendLogic {
  def appendQueryForBanner(
    advertisesQueryIn : Query[Advertise, AdvertiseRow, Seq],
    maybeBanner : Option[BannerImpressionModel]
  ) : Query[Advertise, AdvertiseRow, Seq] = {
    if (EmptyValidateHelper.isEmpty(maybeBanner)) {
      return advertisesQueryIn
    }

    val banner = maybeBanner.get
    var advertisesQuery = advertisesQueryIn.filter(w => w.isbanner === AppConstants.TrueInteger)
    if (EmptyValidateHelper.isDefinedIntPlusPositive(banner.wmax)) {
      val maxWidth = banner.wmax.get
      advertisesQuery = advertisesQuery.filter(ad => ad.maxwidth <= maxWidth)
    }

    if (EmptyValidateHelper.isDefinedIntPlusPositive(banner.wmin)) {
      val minWidth = banner.wmin.get
      advertisesQuery = advertisesQuery.filter(ad => ad.minwidth >= minWidth)
    }

    if (EmptyValidateHelper.isDefinedIntPlusPositive(banner.hmax)) {
      val maxHeight = banner.hmax.get
      advertisesQuery = advertisesQuery.filter(ad => ad.maxheight <= maxHeight)
    }

    if (EmptyValidateHelper.isDefinedIntPlusPositive(banner.hmin)) {
      val minHeight = banner.hmin.get
      advertisesQuery = advertisesQuery.filter(ad => ad.minheight >= minHeight)
    }

    if (EmptyValidateHelper.isDefinedIntPlusPositive(banner.h)) {
      advertisesQuery = advertisesQuery.filter(w => w.height === banner.h.get)
    }

    if (EmptyValidateHelper.isDefinedIntPlusPositive(banner.w)) {
      advertisesQuery = advertisesQuery.filter(w => w.width === banner.w.get)
    }

    advertisesQuery
  }
}
