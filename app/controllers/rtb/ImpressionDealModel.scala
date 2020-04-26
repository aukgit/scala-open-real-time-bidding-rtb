package controllers.rtb

import shared.com.ortb.importedModels.biddingRequests.ImpressionModel

case class ImpressionDealModel (
  impression: ImpressionModel,
  deal: Option[Double]
)
