package shared.com.ortb.demadSidePlatforms.traits.logics

import shared.com.ortb.model.auctionbid.DemandSidePlatformBidResponseModel
import shared.com.ortb.model.results.DemandSidePlatformBiddingRequestModel

trait DemandSidePlatformBiddingLogic
  extends DemandSidePlatformStaticBidResponseLogic
    with DemandSidePlatformActualBidResponseLogic{
  def isStatic : Boolean

  def getBidPrices(request : DemandSidePlatformBiddingRequestModel) : Option[DemandSidePlatformBidResponseModel]

  def getBid(
    request : DemandSidePlatformBiddingRequestModel) : Option[DemandSidePlatformBidResponseModel] = {

    if (isStatic) {
      return getBidStatic(request)
    }

    getBidActual(request)
  }
}
