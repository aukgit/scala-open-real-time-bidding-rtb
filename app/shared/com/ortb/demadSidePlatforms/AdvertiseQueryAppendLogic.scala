package shared.com.ortb.demadSidePlatforms
import shared.com.ortb.model.auctionbid.biddingRequests.banners.BannerImpressionModel
import shared.com.ortb.persistent.schema.Tables
import shared.io.helpers.EmptyValidateHelper
import slick.lifted.Query
import slick.jdbc.SQLiteProfile.api._

trait AdvertiseQueryAppendLogic {

  def appendQueryForBanner(
    advertisesQueryIn : Query[Tables.Advertise, Tables.AdvertiseRow, Seq],
    banner : BannerImpressionModel
  ) : Query[Tables.Advertise, Tables.AdvertiseRow, Seq] = {
    var advertisesQuery = advertisesQueryIn
    if (EmptyValidateHelper.isDefined(banner.wmax)) {
      val maxWidth = banner.wmax.get
      advertisesQuery = advertisesQuery.filter(ad => ad.maxwidth <= maxWidth)
    }

    if (EmptyValidateHelper.isDefined(banner.wmin)) {
      val minWidth = banner.wmin.get
      advertisesQuery = advertisesQuery.filter(ad => ad.minwidth >= minWidth)
    }

    if (EmptyValidateHelper.isDefined(banner.hmax)) {
      val maxHeight = banner.hmax.get
      advertisesQuery = advertisesQuery.filter(ad => ad.maxheight <= maxHeight)
    }

    if (EmptyValidateHelper.isDefined(banner.hmin)) {
      val minHeight = banner.hmin.get
      advertisesQuery = advertisesQuery.filter(ad => ad.minheight >= minHeight)
    }

    advertisesQuery
  }
}
