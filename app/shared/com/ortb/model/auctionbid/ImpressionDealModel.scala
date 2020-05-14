package shared.com.ortb.model.auctionbid

import shared.com.ortb.model.auctionbid.biddingRequests.ImpressionModel

case class ImpressionDealModel(
  impression : ImpressionModel,
  deal : Double
) {
  lazy val hasAnyDeal : Boolean = deal > 0
}
