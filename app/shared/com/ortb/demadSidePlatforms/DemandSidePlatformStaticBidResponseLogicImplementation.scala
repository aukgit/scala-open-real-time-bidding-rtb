package shared.com.ortb.demadSidePlatforms

import io.circe.generic.auto._
import shared.com.ortb.constants.AppConstants
import shared.com.ortb.demadSidePlatforms.traits.logics.DemandSidePlatformStaticBidResponseLogic
import shared.com.ortb.demadSidePlatforms.traits.properties.{ DemandSidePlatformBiddingProperties, DemandSidePlatformCorePropertiesContracts }
import shared.com.ortb.model.auctionbid.biddingRequests.{ BidRequestModel, ImpressionModel }
import shared.com.ortb.model.auctionbid.{ DemandSidePlatformBidResponseModel, ImpressionDealModel }
import shared.com.ortb.model.config.DemandSidePlatformConfigurationModel
import shared.com.ortb.model.logging.CallStackModel
import shared.com.ortb.model.results.DemandSidePlatformBiddingRequestWrapperModel
import shared.com.ortb.persistent.schema._
import shared.io.extensions.TypeConvertExtensions._
import shared.io.helpers.JodaDateTimeHelper

import scala.collection.mutable.ArrayBuffer
import scala.util.Try

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
      .toBoolInt

    val hasVideo = impression
      .video
      .isDefined
      .toBoolInt

    val minMaxHeightWidth = impression.minMaxHeightWidth
    val impressionToString = s"impression(${ impression.toString })"
    val dealString = s"deal($deal)"

    Tables.AdvertiseRow(
      advertiseid = AppConstants.NewRecordIntId,
      campaignid = AppConstants.NewRecordIntId,
      banneradvertisetypeid = 5,
      advertisetitle = title,
      contentcontextid = Some(5),
      bidurl = s"bidUrl($dealString)$impressionToString",
      iframehtml = s"Iframe($dealString)$impressionToString".toSome,
      iscountryspecific = 0,
      isbanner = hasBanner,
      isvideo = hasVideo,
      isnative = 0,
      impressioncount = 1,
      height = minMaxHeightWidth.height,
      width = minMaxHeightWidth.width,
      minheight = minMaxHeightWidth.minHeight,
      minwidth = minMaxHeightWidth.minWidth,
      maxheight = minMaxHeightWidth.maxHeight,
      maxwidth = minMaxHeightWidth.maxWidth,
      isheightwidthempty = minMaxHeightWidth.isEmptyHeightWidth.toBoolInt,
      ismaxheightwidthempty = minMaxHeightWidth.isMaxHeightWidthEmpty.toBoolInt,
      isminheightwidthempty = minMaxHeightWidth.isMinHeightWidthEmpty.toBoolInt,
      hasagerestriction = 0,
      minage = Some(0),
      maxage = Some(0),
      duration = 0,
      0,
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
    //    Some(dspBidderResultModel)
    throw new NotImplementedError()
  }

  override def getStaticBidRequestToBidRequestRow(bidRequest : BidRequestModel) : Tables.BidrequestRow = {
    val tryCountries = Try(bidRequest.device.get.geo.get.country)
    val countries = if (tryCountries.isSuccess) tryCountries.get else None
    val tryCity = Try(bidRequest.device.get.geo.get.city)
    val city = if (tryCity.isSuccess) tryCity.get else None
    val json = bidRequest.toJsonString
    val impressions = bidRequest.imp
    val hasBannerHasVideoArray = impressions.forAnyGroup(w => w.hasBanner, y => y.hasVideo)
    val hasBanner = hasBannerHasVideoArray(0).toBoolInt
    val hasVideo = hasBannerHasVideoArray(1).toBoolInt

    Tables.BidrequestRow(
      bidrequestid = bidRequest.id.toInt,
      demandsideplatformid = demandSideId,
      isbanner = hasBanner,
      isvideo = hasVideo,
      auctionid = None,
      countries = countries,
      cities = city, targetedcities = city,
      rawbidrequestjson = json,
      createddatetimestamp = JodaDateTimeHelper.nowUtcJavaInstant
    )
  }
}
