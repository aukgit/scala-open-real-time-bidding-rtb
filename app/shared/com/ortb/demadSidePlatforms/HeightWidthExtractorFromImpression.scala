package shared.com.ortb.demadSidePlatforms

import shared.com.ortb.model.MinMaxHeightWidthModel
import shared.com.ortb.model.auctionbid.biddingRequests.ImpressionModel
import shared.com.ortb.model.dimensions.{ EmptyHeightWidthModel, HeightWidthBaseModel, HeightWidthModel, MinMaxHeightWidthModel }

trait HeightWidthExtractorFromImpression {
  def getMinMaxHeightWidths(impression : ImpressionModel) : MinMaxHeightWidthModel = {
    val heightWidth = getHeightWidth(impression)
    val maxHeightWidth = getMaxHeightWidth(impression)
    val minHeightWidth = getMinHeightWidth(impression)

    MinMaxHeightWidthModel(
      minHeightWidth,
      heightWidth,
      maxHeightWidth)
  }

  def getHeightWidth(impression : ImpressionModel) : HeightWidthBaseModel = {
    if (impression.hasBanner) {
      return impression.banner.get
    }

    if (impression.hasVideo) {
      return impression.video.get
    }

    EmptyHeightWidthModel()
  }

  def getMaxHeightWidth(impression : ImpressionModel) : HeightWidthBaseModel = {
    if (impression.hasBanner) {
      val banner = impression.banner.get

      return HeightWidthModel(banner.hmax, banner.wmax)
    }

    EmptyHeightWidthModel()
  }

  def getMinHeightWidth(impression : ImpressionModel) : HeightWidthBaseModel = {
    if (impression.hasBanner) {
      val banner = impression.banner.get

      return HeightWidthModel(banner.hmin, banner.wmin)
    }

    EmptyHeightWidthModel()
  }
}

object HeightWidthExtractorFromImpression extends HeightWidthExtractorFromImpression
