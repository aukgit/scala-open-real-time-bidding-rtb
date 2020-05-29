package shared.com.ortb.model.config

case class DemandSidePlatformNoBiddingResponseConfigurationModel(
  isWellFormedBidRequest : Boolean,
  performTechnicalErrorOnDsp : Array[Int],
  wellFormedBidRequestSample : DemandSidePlatformWellFormedBidRequestSampleModel
)
