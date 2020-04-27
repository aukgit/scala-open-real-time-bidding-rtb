package shared.com.ortb.demadSidePlatforms.traits.logics

import shared.com.ortb.model.DspBidderResultModel
import shared.com.ortb.model.results.DspBidderRequestModel

trait DemandSidePlatformActualBidResponseLogic {
  def getBidActual(
    request : DspBidderRequestModel) : Option[DspBidderResultModel]

  def getBidActualNoContent(
    request : DspBidderRequestModel) : Option[DspBidderResultModel]
}
