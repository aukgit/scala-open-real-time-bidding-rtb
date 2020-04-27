package shared.com.ortb.model.results

import shared.com.ortb.demadSidePlatforms.DemandSidePlatformSimulatorServiceApiController
import shared.com.ortb.importedModels.biddingRequests.BidRequest

case class DspBidderRequestModel(

  bidRequest  : BidRequest,
  dspId       : Int)
