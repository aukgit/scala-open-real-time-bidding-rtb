package shared.com.ortb.adapters.implementations

import com.google.inject.Inject
import shared.com.ortb.adapters.traits.{ BannerModelAdapter, BidResponseModelAdapter }
import shared.com.ortb.constants.AppConstants
import shared.com.ortb.demadSidePlatforms.traits.properties.DemandSidePlatformCorePropertiesContracts
import shared.com.ortb.enumeration.NoBidResponseType.NoBidResponseType
import shared.com.ortb.model.auctionbid.biddingRequests.banners.{ BannerImpressionModel, SimpleBannerModel }
import shared.com.ortb.model.auctionbid.bidresponses.{ BidResponseModel, BidResponseModelWrapper }
import shared.com.ortb.model.results.DemandSidePlatformBiddingRequestWrapperModel
import shared.com.ortb.persistent.schema.Tables._
import shared.io.helpers.NumberHelper

class BidResponseModelAdapterConcreteImplementation @Inject()(
  demandSidePlatformCorePropertiesContracts : DemandSidePlatformCorePropertiesContracts)
  extends BidResponseModelAdapter {

  def getConvertBidResponseRowToBidResponse(
    dspId : Int,
    bidResponseRow : BidresponseRow) : BidResponseModel = {
//    val isNoBidResponse = PrimitiveTypeHelper
//      .IntEnhancement(bidResponseRow.issendnobidresponse).toBoolean
//
//    if (isNoBidResponse) {
//      // no response bid
//      val nbr = bidResponseRow.nobidresponsetypeid
//
//      return BidResponseModel(
//        bidResponseRow.bidresponseid.toString,
//        None,
//        Some(bidResponseRow.bidrequestid.toString),
//        nbr = nbr)
//    }
//
//          val seatBid =SeatBidModel()
//    return
    throw new NotImplementedError()
  }

  /**
   * A well-formed no bid response with a reason code: {"id": "1234567890", "seatbid": [], "nbr": 2}
   *
   * @param request
   * @param noBidResponseType
   *
   * @return
   */
  override def noBidResponse(
    request : DemandSidePlatformBiddingRequestWrapperModel,
    noBidResponseType : NoBidResponseType) : BidResponseModelWrapper = {
    // create a bid response.
    val repository =
      demandSidePlatformCorePropertiesContracts.repositories.bidResponseRepository
    val bidRequestId = Some(request.bidRequestRow.bidrequestid)
//    val row = BidresponseRow(
//      -1,
//      issendnobidresponse = 1,
//      bidrequestid = bidRequestId,
//      currency = AppConstants.CurrencyUsd,
//      createddate = 0, cr2 = None)
//
//    val createdResponse = repository.add(row)
//    createdResponse.getIdAsInt
//
//    val w = BidResponseModelWrapper()
    throw new NotImplementedError()
  }
}


class BannerModelAdapterConcreteImplementation extends BannerModelAdapter {
  def getSimpleBanner(bannerImpressionModel : BannerImpressionModel) : SimpleBannerModel = {
    if (bannerImpressionModel == null) {
      return null
    }

    SimpleBannerModel(
      bannerImpressionModel.id,
      wmin = NumberHelper.getAsInt(bannerImpressionModel.wmin),
      wmax = NumberHelper.getAsInt(bannerImpressionModel.wmax),
      w = NumberHelper.getAsInt(bannerImpressionModel.w),
      hmin = NumberHelper.getAsInt(bannerImpressionModel.hmin),
      hmax = NumberHelper.getAsInt(bannerImpressionModel.hmax),
      h = NumberHelper.getAsInt(bannerImpressionModel.h))
  }
}
