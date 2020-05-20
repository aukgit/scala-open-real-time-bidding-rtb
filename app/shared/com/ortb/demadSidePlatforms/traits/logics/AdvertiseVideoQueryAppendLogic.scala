package shared.com.ortb.demadSidePlatforms.traits.logics

import shared.com.ortb.constants.AppConstants
import slick.jdbc.SQLiteProfile.api._
import shared.com.ortb.model.auctionbid.biddingRequests.VideoImpressionModel
import shared.com.ortb.persistent.schema.Tables
import shared.io.helpers.EmptyValidateHelper
import slick.lifted.Query

trait AdvertiseVideoQueryAppendLogic {
  def appendQueryForVideo(
    advertisesQueryIn : Query[Tables.Advertise, Tables.AdvertiseRow, Seq],
    maybeVideo : Option[VideoImpressionModel]
  ) : Query[Tables.Advertise, Tables.AdvertiseRow, Seq] = {
    if (EmptyValidateHelper.isEmpty(maybeVideo)) {
      return advertisesQueryIn
    }

    val video = maybeVideo.get
    var advertisesQuery = advertisesQueryIn.filter(w => w.isvideo === AppConstants.TrueInteger)

    if (EmptyValidateHelper.isDefinedIntPlusPositive(video.h)) {
      advertisesQuery = advertisesQuery.filter(ad => ad.height === video.h)
    }

    if (EmptyValidateHelper.isDefinedIntPlusPositive(video.w)) {
      advertisesQuery = advertisesQuery.filter(ad => ad.width === video.w)
    }

    advertisesQuery
  }
}
