package shared.com.ortb.model.results

import controllers.rtb.DemandSidePlatformSimulatorServiceApiController
import shared.com.ortb.importedModels.biddingRequests.BidRequest

case class DspBidderRequestModel(

  bidRequest  : BidRequest,
  dspId       : Int)
