package shared.com.ortb.demadSidePlatforms.traits.logics

import shared.com.ortb.model.auctionbid.DemandSidePlatformBidResponseModel
import shared.com.ortb.model.results.DemandSidePlatformBiddingRequestWrapperModel

trait DemandSidePlatformBiddingLogic
  extends DemandSidePlatformStaticBidResponseLogic
    with DemandSidePlatformActualBidResponseLogic {
  def isStatic : Boolean

  def getBidPrices(request : DemandSidePlatformBiddingRequestWrapperModel) : Option[DemandSidePlatformBidResponseModel]

  def getBid(
    request : DemandSidePlatformBiddingRequestWrapperModel) : Option[DemandSidePlatformBidResponseModel] = {

    if (isStatic) {
      return getBidStatic(request)
    }

    getBidActual(request)
  }
}
