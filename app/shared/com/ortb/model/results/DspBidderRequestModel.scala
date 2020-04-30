package shared.com.ortb.model.results

import shared.com.ortb.demadSidePlatforms.DemandSidePlatformSimulatorServiceApiController
import shared.com.ortb.importedModels.biddingRequests.BidRequestModel

case class DspBidderRequestModel(
  bidRequest  : BidRequestModel,
  dspId       : Int)
