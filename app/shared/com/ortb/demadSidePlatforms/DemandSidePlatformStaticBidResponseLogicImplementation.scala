package shared.com.ortb.demadSidePlatforms

import com.github.dwickern.macros.NameOf._
import io.circe.generic.auto._
import shared.com.ortb.constants.AppConstants
import shared.com.ortb.demadSidePlatforms.traits.logics.DemandSidePlatformStaticBidResponseLogic
import shared.com.ortb.demadSidePlatforms.traits.properties.{ DemandSidePlatformBiddingProperties, DemandSidePlatformCorePropertiesContracts }
import shared.com.ortb.enumeration.NoBidResponseType
import shared.com.ortb.model.auctionbid.biddingRequests.{ BidRequestModel, ImpressionModel }
import shared.com.ortb.model.auctionbid.bidresponses.{ BidModel, BidResponseModel, BidResponseModelWrapper, SeatBidModel }
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

  override def getStaticBid(
    request : DemandSidePlatformBiddingRequestWrapperModel) : Option[DemandSidePlatformBidResponseModel] = {
    val impressions : Seq[ImpressionModel] = request.bidRequest.imp
    val length = impressions.length
    val deals = new ArrayBuffer[ImpressionDealModel](length)
    val bidModels = new ArrayBuffer[BidModel](length)
    val staticBidModel = AppConstants
      .BiddingConstants
      .staticBidModel

    val winNoticeUrlWithPlaceholder = coreProperties
      .demandSidePlatformConfiguration
      .winNoticeUrlWithPlaceholder
    val winNoticePlaceholder = AppConstants
      .BiddingConstants
      .winNoticePlaceHolderName

    for (impression <- impressions) {
      val minMaxHeightWidth = impression.minMaxHeightWidth
      val deal : Double = getStaticBidPrice(impression)
      val advertise = createStaticAdvertise(impression, deal, "Title")
      val impressionDealModel = ImpressionDealModel(impression, advertise, deal)
      deals.addOne(impressionDealModel)
      val nUrl = winNoticeUrlWithPlaceholder.replace(winNoticePlaceholder, impression.id)

      val bidModel = staticBidModel.copy(
        impid = impression.id,
        price = deal,
        adid = advertise.advertiseid.toStringSome,
        nurl = nUrl.toSome,
        h = minMaxHeightWidth.maybeHeight,
        w = minMaxHeightWidth.maybeWidth)

      bidModels.addOne(bidModel)
    }

    val seatBidModel = SeatBidModel(
      bidModels.toList,
      seat = s"SeatBidID / DSP ID : $demandSideId".toSome)

    val bidResponse = BidResponseModel(
      "static : BidResponse Id",
      seatBidModel.toMakeListSome,
      None,
      nbr = Some(NoBidResponseType.UnknownError.value))

    val bidResponseWrapper = BidResponseModelWrapper(Some(bidResponse))

    val dspBidderResultModel =
      DemandSidePlatformBidResponseModel(
        request,
        request.bidRequest,
        bidResponseWrapper = bidResponseWrapper,
        deals.toList.toSome)

    dspBidderResultModel.toSome
  }

  def getStaticBidPrice(impression : ImpressionModel) : Double = {
    val ownRandomGuess = coreProperties
      .currentServiceModel
      .ownBiddingRandomRange
      .randomInBetweenRange
    val demandSidePlatformConfigurationModel = coreProperties
      .demandSidePlatformConfiguration
    val globalRandomGuess = demandSidePlatformConfigurationModel
      .globalRandomRange
      .randomInBetweenRange

    val randomGuess = ownRandomGuess + globalRandomGuess
    var deal : Double = randomGuess +
      defaultIncrementNumber +
      randomGuess / 2 +
      demandSidePlatformConfigurationModel.defaultBidStaticDeal

    if (impression.bidfloor.isDefined) {
      deal += impression.bidfloor.get
    } else {
      deal += defaultStaticDeal - defaultIncrementNumber / 2
    }

    deal
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

  override def getStaticBidNoContent(
    request : DemandSidePlatformBiddingRequestWrapperModel) : Option[DemandSidePlatformBidResponseModel] = {
    None
  }

  override def getStaticBidRequestToBidRequestRow(bidRequest : BidRequestModel) : Tables.BidrequestRow = {
    val tryCountries = Try(bidRequest.device.get.geo.get.country)
    val countries = if (tryCountries.isSuccess) tryCountries.get else None
    val tryCity = Try(bidRequest.device.get.geo.get.city)
    val city = if (tryCity.isSuccess) tryCity.get else None
    val json = bidRequest.toJsonString
    val impressions = bidRequest.imp
    val hasBannerHasVideoArray = impressions.forAnyGroup(w => w.hasBanner, y => y.hasVideo)
    val hasBanner = hasBannerHasVideoArray.head.toBoolInt
    val hasVideo = hasBannerHasVideoArray.last.toBoolInt

    Tables.BidrequestRow(
      bidrequestid = bidRequest.id.toIntOrDefault(),
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
