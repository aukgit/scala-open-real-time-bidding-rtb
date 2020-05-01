package shared.com.ortb.adapters.traits

import com.google.inject.Inject
import shared.com.ortb.constants.AppConstants
import shared.com.ortb.demadSidePlatforms.traits.properties.DemandSidePlatformCorePropertiesContracts
import shared.com.ortb.enumeration.NoBidResponseType.NoBidResponseType
import shared.com.ortb.model.auctionbid.biddingRequests.banners.{ BannerImpressionModel, SimpleBannerModel }
import shared.com.ortb.model.auctionbid.bidresponses.{ BidResponseModel, BidResponseModelWrapper, SeatBidModel }
import shared.com.ortb.model.results.DemandSidePlatformBiddingRequestModel
import shared.com.ortb.persistent.schema.Tables._
import shared.io.helpers.{ NumberHelper, PrimitiveTypeHelper }

class BidResponseModelAdapterConcreteImplementation @Inject()(
  demandSidePlatformCorePropertiesContracts : DemandSidePlatformCorePropertiesContracts)
  extends BidResponseModelAdapter {

  def getConvertBidResponseRowToBidResponse(
    dspId : Int,
    bidResponseRow : BidresponseRow) : BidResponseModel = {
    val isNoBidResponse = PrimitiveTypeHelper
      .IntEnhancement(bidResponseRow.issendnobidresponse).toBoolean

    if (isNoBidResponse) {
      // no response bid
      val nbr = bidResponseRow.nobidresponsetypeid

      return BidResponseModel(
        bidResponseRow.bidresponseid.toString,
        None,
        Some(bidResponseRow.bidrequestid.toString),
        nbr = nbr)
    }

          val seatBid =SeatBidModel()
    return

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
    request : DemandSidePlatformBiddingRequestModel,
    noBidResponseType : NoBidResponseType) : BidResponseModelWrapper = {
    // create a bid response.
    val repository =
      demandSidePlatformCorePropertiesContracts.repositories.bidResponseRepository
    val bidRequestId = Some(request.bidRequestRow.bidrequestid)
    val row = BidresponseRow(
      -1,
      issendnobidresponse = 1,
      bidrequestid = bidRequestId,
      currency = AppConstants.CurrencyUsd)

    val createdResponse = repository.add(row)
    createdResponse.getIdAsInt

    val w = BidResponseModelWrapper()
    throw new NotImplementedError()
  }
}


class BannerModelAdapterConcreteImplementation extends BannerModelAdapter {
  def getSimpleBanner(bannerModel : BannerImpressionModel) : SimpleBannerModel = {
    if (bannerModel == null) {
      return null
    }

    SimpleBannerModel(
      bannerModel.id,
      wmin = NumberHelper.getAsInt(bannerModel.wmin),
      wmax = NumberHelper.getAsInt(bannerModel.wmax),
      w = NumberHelper.getAsInt(bannerModel.w),
      hmin = NumberHelper.getAsInt(bannerModel.hmin),
      hmax = NumberHelper.getAsInt(bannerModel.hmax),
      h = NumberHelper.getAsInt(bannerModel.h))
  }
}
