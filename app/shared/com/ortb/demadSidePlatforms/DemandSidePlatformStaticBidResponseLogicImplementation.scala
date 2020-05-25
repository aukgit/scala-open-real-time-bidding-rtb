package shared.com.ortb.demadSidePlatforms

import shared.com.ortb.constants.AppConstants
import shared.com.ortb.demadSidePlatforms.traits.logics.DemandSidePlatformStaticBidResponseLogic
import shared.com.ortb.demadSidePlatforms.traits.properties.{ DemandSidePlatformBiddingProperties, DemandSidePlatformCorePropertiesContracts }
import shared.com.ortb.model.auctionbid.biddingRequests.ImpressionModel
import shared.com.ortb.model.auctionbid.{ DemandSidePlatformBidResponseModel, ImpressionDealModel }
import shared.com.ortb.model.config.DemandSidePlatformConfigurationModel
import shared.com.ortb.model.logging.CallStackModel
import shared.com.ortb.model.results.DemandSidePlatformBiddingRequestWrapperModel
import shared.com.ortb.model.{ EmptyHeightWidthModel, HeightWidthBaseModel, HeightWidthModel, MinMaxHeightWidthModel }
import shared.com.ortb.persistent.schema.Tables
import shared.io.helpers.JodaDateTimeHelper
import shared.io.extensions.TypeConvertExtensions._

import scala.collection.mutable.ArrayBuffer

class DemandSidePlatformStaticBidResponseLogicImplementation(
  val demandSideId : Int,
  val coreProperties : DemandSidePlatformCorePropertiesContracts)
  extends DemandSidePlatformStaticBidResponseLogic
    with DemandSidePlatformBiddingProperties {

  lazy override val demandSidePlatformConfiguration : DemandSidePlatformConfigurationModel =
    coreProperties.demandSidePlatformConfiguration

  override def getBidStatic(
    request : DemandSidePlatformBiddingRequestWrapperModel) : Option[DemandSidePlatformBidResponseModel] = {
    val impressions : Seq[ImpressionModel] = request.bidRequest.imp
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
        request.bidRequest,
        bidResponseWrapper = null,
        Some(deals.toList))
    dspBidderResultModel.addCallStacks(callStacks)

    Some(dspBidderResultModel)
  }

  def createStaticAdvertise(
    impression : ImpressionModel,
    deal : Double,
    title : String = "Dummy Static Advertise") : Tables.AdvertiseRow = {

    val hasBanner = impression
      .banner
      .isDefined
      .toInt

    val hasVideo = impression
      .video
      .isDefined
      .toInt

    val minMaxHeightWidth = impression.minMaxHeightWidth
    val impressionToString = s"impression(${ impression.toString })"
    val dealString = s"deal($deal)"

    Tables.AdvertiseRow(
      AppConstants.NewRecordIntId,
      AppConstants.NewRecordIntId,
      5,
      title,
      Some(5),
      s"bidUrl($dealString)$impressionToString",
      Some(s"Iframe($dealString)$impressionToString"),
      0,
      Some(hasBanner),
      hasVideo,
      1,
      minMaxHeightWidth.height,
      minMaxHeightWidth.width,
      minMaxHeightWidth.minHeight,
      minMaxHeightWidth.minWidth,
      minMaxHeightWidth.maxHeight,
      minMaxHeightWidth.maxWidth,
      Some(minMaxHeightWidth.isEmptyHeightWidth.toInt),
      Some(minMaxHeightWidth.isMaxHeightWithEmpty.toInt),
      Some(minMaxHeightWidth.isMinHeightWithEmpty.toInt),
      0,
      Some(0),
      Some(0),
      JodaDateTimeHelper.nowUtcJavaInstant)
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
