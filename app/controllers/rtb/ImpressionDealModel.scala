package controllers.rtb

import shared.com.ortb.importedModels.biddingRequests.Impression

case class ImpressionDealModel (
  impression: Impression,
  deal: Option[Double]
)
