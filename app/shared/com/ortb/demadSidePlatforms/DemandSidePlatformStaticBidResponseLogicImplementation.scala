package shared.com.ortb.demadSidePlatforms

import shared.com.ortb.demadSidePlatforms.traits.logics.DemandSidePlatformStaticBidResponseLogic
import shared.com.ortb.demadSidePlatforms.traits.properties.{ DemandSidePlatformBiddingProperties, DemandSidePlatformCorePropertiesContracts }
import shared.com.ortb.model.auctionbid.biddingRequests.ImpressionModel
import shared.com.ortb.model.auctionbid.{ DemandSidePlatformBidResponseModel, ImpressionDealModel }
import shared.com.ortb.model.config.DemandSidePlatformConfigurationModel
import shared.com.ortb.model.logging.CallStackModel
import shared.com.ortb.model.results.DemandSidePlatformBiddingRequestWrapperModel
import shared.com.ortb.model.{ EmptyHeightWidthModel, HeightWidthBaseModel, HeightWidthModel }
import shared.com.ortb.persistent.schema.Tables
import shared.io.helpers.{ JodaDateTimeHelper, PrimitiveTypeHelper }

import scala.collection.mutable.ArrayBuffer

class DemandSidePlatformStaticBidResponseLogicImplementation
(demandSidePlatformCoreProperties : DemandSidePlatformCorePropertiesContracts)
  extends DemandSidePlatformStaticBidResponseLogic with DemandSidePlatformBiddingProperties {

  lazy override val demandSidePlatformConfiguration : DemandSidePlatformConfigurationModel =
    demandSidePlatformCoreProperties.demandSidePlatformConfiguration

  override def getBidStatic(
    request : DemandSidePlatformBiddingRequestWrapperModel) : Option[DemandSidePlatformBidResponseModel] = {
    val impressions : Seq[ImpressionModel] = request.bidRequestModel.imp.get
    val length = impressions.length
    val deals = new ArrayBuffer[ImpressionDealModel](length)
    val callStacks = new ArrayBuffer[CallStackModel](length)

    for (impression <- impressions) {
      if (impression.bidfloor.isDefined) {
        val deal : Double = impression.bidfloor.get + defaultIncrementNumber
        val advertise = createStaticAdvertise(impression, deal, "Title")
        val impressionDealModel = ImpressionDealModel(impression, advertise, deal)
        deals.addOne(impressionDealModel)

        val callStackModel = CallStackModel(
          deal = deal,
          performingAction =
            s"[getBidStatic] -> adding deals($deal) for given bid request.",
          isServedAnyDeal = true
        )

        callStacks.addOne(callStackModel)
      } else {
        val advertise = createStaticAdvertise(impression, defaultStaticDeal, "Title")
        deals.addOne(ImpressionDealModel(impression, advertise, defaultStaticDeal))

        val callStackModel = CallStackModel(
          deal = defaultStaticDeal,
          performingAction =
            s"[getBidStatic] -> adding deals($defaultStaticDeal) for given bid request.",
          isServedAnyDeal = true
        )

        callStacks.addOne(callStackModel)
      }
    }

    val dspBidderResultModel =
      DemandSidePlatformBidResponseModel(
        request,
        request.bidRequestModel,
        bidResponseWrapper = null,
        Some(deals.toList))
    dspBidderResultModel.addCallStacks(callStacks)

    Some(dspBidderResultModel)
  }

  def createStaticAdvertise(
    impression : ImpressionModel,
    deal : Double,
    title : String = "Dummy Static Advertise") : Tables.AdvertiseRow = {
    val hasBanner = PrimitiveTypeHelper
      .BooleanEnhancement(impression.banner.isDefined)
      .toInt
    val hasVideo = PrimitiveTypeHelper
      .BooleanEnhancement(impression.video.isDefined)
      .toInt

    val heightWidth = getHeightWidth(impression)
    val maxHeightWidth = getMaxHeightWidth(impression)
    val minHeightWidth = getMinHeightWidth(impression)

    Tables.AdvertiseRow(
      -1,
      -1,
      5,
      title,
      Some(5),
      "bidUrl()",
      Some("Iframe()"),
      0,
      Some(hasBanner),
      hasVideo,
      1,
      heightWidth.height,
      heightWidth.width,
      minHeightWidth.height,
      minHeightWidth.width,
      maxHeightWidth.height,
      maxHeightWidth.width,
      0,
      Some(0),
      Some(0),
      JodaDateTimeHelper.nowUtcJavaInstant)
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

  override def getBidStaticNoContent(
    request : DemandSidePlatformBiddingRequestWrapperModel) : Option[DemandSidePlatformBidResponseModel] = {
    //    val dspBidderResultModel =
    //      model.DemandSidePlatformBidResponseModel(
    //        request,
    //        request.bidRequest,
    //        isNoContent = true,
    //        BidResponseModelWrapper = null)
    //
    //    val callStackModel = CallStackModel(
    //      deal = demandSidePlatformCoreProperties.noDealPrice,
    //      performingAction = s"[getBidStaticNoContent] -> No deals.",
    //      isServedAnyDeal = false
    //    )
    //
    //    dspBidderResultModel.addCallStack(callStackModel)
    //
    //    Some(dspBidderResultModel)
    throw new NotImplementedError()

  }
}
