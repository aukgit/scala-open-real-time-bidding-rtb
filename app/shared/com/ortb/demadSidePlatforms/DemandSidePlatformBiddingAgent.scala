package shared.com.ortb.demadSidePlatforms

import shared.com.ortb.constants.AppConstants
import shared.com.ortb.demadSidePlatforms.traits.getters._
import shared.com.ortb.demadSidePlatforms.traits.logics._
import shared.com.ortb.demadSidePlatforms.traits.properties.{ DemandSidePlatformBiddingProperties, DemandSidePlatformCorePropertiesContracts }
import shared.com.ortb.demadSidePlatforms.traits.{ AddNewAdvertiseOnNotFound, DefaultActualNoContentResponse, RunQuery }
import shared.com.ortb.enumeration.DemandSidePlatformBiddingAlgorithmType.DemandSidePlatformBiddingAlgorithmType
import shared.com.ortb.enumeration.NoBidResponseType
import shared.com.ortb.manager.traits.CreateDefaultContext
import shared.com.ortb.model.auctionbid.bidresponses.{ BidResponseModel, BidResponseModelWrapper }
import shared.com.ortb.model.auctionbid.{ DemandSidePlatformBidResponseModel, ImpressionDealModel }
import shared.com.ortb.model.config.DemandSidePlatformConfigurationModel
import shared.com.ortb.model.results.DemandSidePlatformBiddingRequestWrapperModel
import shared.com.ortb.persistent.schema.Tables._
import shared.io.helpers.{ EmptyValidateHelper, JodaDateTimeHelper }

import scala.concurrent.ExecutionContext

class DemandSidePlatformBiddingAgent(
  val coreProperties : DemandSidePlatformCorePropertiesContracts,
  val demandSideId : Int,
  val algorithmType : DemandSidePlatformBiddingAlgorithmType)
  extends DemandSidePlatformBiddingLogic
    with DemandSidePlatformBiddingProperties
    with AddNewAdvertiseOnNotFound
    with CreateDefaultContext
    with BiddableInfoModelsGetter
    with AdvertiseBannerQueryAppendLogic
    with AdvertiseVideoQueryAppendLogic
    with RunQuery
    with FailedBidsGetter
    with ImpressionDealsGetter
    with DefaultActualNoContentResponse {

  override def createDefaultContext() : ExecutionContext =
    executionContextManager.defaultExecutionContext

  lazy override val demandSidePlatformConfiguration : DemandSidePlatformConfigurationModel =
    coreProperties.demandSidePlatformConfiguration

  def getBidStatic(request : DemandSidePlatformBiddingRequestWrapperModel) : Option[DemandSidePlatformBidResponseModel] =
    demandSidePlatformStaticBidResponseLogic.getBidStatic(request)

  override def getBidStaticNoContent(request : DemandSidePlatformBiddingRequestWrapperModel) :
  Option[DemandSidePlatformBidResponseModel] =
    demandSidePlatformStaticBidResponseLogic.getBidStaticNoContent(request)

  override def getBidPrices(
    request : DemandSidePlatformBiddingRequestWrapperModel) : Option[DemandSidePlatformBidResponseModel] = ???

  override def getBidActual(
    request : DemandSidePlatformBiddingRequestWrapperModel) : Option[DemandSidePlatformBidResponseModel] = {
    val biddableImpressionInfoModels = getBiddableImpressionInfoModels(
      request : DemandSidePlatformBiddingRequestWrapperModel)

    val impressionDeals =
      getImpressionInfosFromImpressionBiddableInfos(
        request,
        biddableImpressionInfoModels)

    if (EmptyValidateHelper.isItemsEmpty(impressionDeals)) {
      return getBidActualNoContent(request)
    }

    createDemandSidePlatformBidResponse(request, impressionDeals)
  }

  def createDemandSidePlatformBidResponse(
    request : DemandSidePlatformBiddingRequestWrapperModel,
    impressionDeals : Option[List[ImpressionDealModel]]) :
  Option[DemandSidePlatformBidResponseModel] = {
    // create bid response
    val bidRequestRow = request.bidRequestRow
    val bidRequestId = Some(bidRequestRow.bidrequestid)
    val auctionId = bidRequestRow.auctionid
    val bidResponse = BidresponseRow(
      -1,
      bidrequestid = bidRequestId,
      isauctionoccured = 1,
      createddatetimestamp = JodaDateTimeHelper.nowUtcJavaInstant
    )

    val bidResponseRepository = coreProperties
      .repositories
      .bidResponseRepository

    val bidResponseId = bidResponseRepository
      .add(bidResponse)
      .getIdAsInt

    // if no bid then empty array for seatbid
    val shouldBid = EmptyValidateHelper.hasAnyItem(impressionDeals) &&
      impressionDeals.get.exists(w => w.hasAnyDeal)

    if (!shouldBid) {
      // return without seatbid
      return emptyNonBiddingResponse(request)
    }

    // create seatbid
    // TODO : look for group winning info if required or asked
    // SeatBid is usually refers to single DSP seat in the auction
    // Thus, from single a DSP seatbid should be only per request.

    val seatBid = SeatbidRow(
      -1,
      bidRequestId,
      bidResponseId,
      auctionId,
      Some(coreProperties.demandSideId),
      createddatetimestamp = JodaDateTimeHelper.nowUtcJavaInstant
    )

    val seatBidRepository = coreProperties
      .repositories
      .seatBidRepository

    val seatBidId = seatBidRepository
      .add(seatBid)
      .getIdAsInt

    // create bid
    // a single seat bid contains number of bids
    // TODO : investigate the usages of SeatBid array
    //        One seatbid can do the job no need for multiple then contains in the definition of the specs
    val bidRepository = coreProperties
      .repositories
      .bidRepository
    impressionDeals.get.foreach(impressionDeal => {
      // TODO : create bid
      val impressionId = impressionDeal.impression.id.toInt
      val impressionToString = s", ImpressionDealModel(${ ImpressionDealModel.toString() })"
      val advertiseId = Some(impressionDeal.advertise.advertiseid)
      val campaignId = 5
      val minMaxHeightWidth = impressionDeal
        .impression
        .minMaxHeightWidth

      val bid = BidRow(
        AppConstants.NewRecordIntId,
        dealbiddingprice = Some(impressionDeal.deal),
        seatbidid = seatBidId.get,
        campaignid = Some(campaignId),
        impressionid = Some(impressionId),
        advertiseid = advertiseId,
        adm = Some(s"adm $impressionToString"),
        iurl = Some(s"iurl $impressionToString"),
        height = minMaxHeightWidth.maybeHeight,
        width = minMaxHeightWidth.maybeWidth,
        createddatetimestamp = JodaDateTimeHelper.nowUtcJavaInstant
      )

      bidRepository.addAsync(bid)
    })

    val bidResponse2 = BidResponseModel(
      bidResponseId.toString,
      None,
      None,
      nbr = Some(NoBidResponseType.UnknownError.value))

    val bidResponseWrapper = BidResponseModelWrapper(Some(bidResponse2))
    val demandSidePlatformBidResponse = DemandSidePlatformBidResponseModel(
      request,
      request.bidRequest,
      bidResponseWrapper)

    Some(demandSidePlatformBidResponse)
  }

  def emptyNonBiddingResponse(request : DemandSidePlatformBiddingRequestWrapperModel) :
  Option[DemandSidePlatformBidResponseModel] = {
    val bidResponse = BidResponseModel(
      "_",
      None,
      None,
      nbr = Some(NoBidResponseType.UnknownError.value))

    val bidResponseWrapper = BidResponseModelWrapper(Some(bidResponse))
    val demandSidePlatformBidResponse = DemandSidePlatformBidResponseModel(
      request,
      request.bidRequest,
      bidResponseWrapper)

    Some(demandSidePlatformBidResponse)
  }

  def getBidResponseRow(bidResponse : Option[BidResponseModel]) : BidresponseRow = ???

  def createNoBidResponseToDbAsync() : Unit = {
    val row = BidresponseRow(
      AppConstants.NewRecordIntId,
      createddatetimestamp = JodaDateTimeHelper.nowUtcJavaInstant)

    coreProperties
      .repositories
      .bidResponseRepository
      .addAsync(row)
  }

  def addBidResponseAsync(response : DemandSidePlatformBidResponseModel) : Unit = {
    if (EmptyValidateHelper.isEmptyDirect(
      response,
      Some("addBidResponse, given response is empty. Nothing to save."))) {
      return
    }

    val bidResponse = response.bidResponseWrapper.bidResponse
    if (EmptyValidateHelper.isEmpty(
      bidResponse,
      Some("addBidResponse, given bidResponse is empty. Nothing to save."))) {
      // create no bid response
      createNoBidResponseToDbAsync()
      return
    }

    val bidResponseRepository = coreProperties
      .repositories
      .bidResponseRepository

    val bidResponseRow = getBidResponseRow(bidResponse)

    bidResponseRepository.addAsync(bidResponseRow)
  }
}
