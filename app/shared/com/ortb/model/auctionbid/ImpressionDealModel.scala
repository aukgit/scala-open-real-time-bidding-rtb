package shared.com.ortb.model.auctionbid

import shared.com.ortb.model.auctionbid.biddingRequests.ImpressionModel

case class ImpressionDealModel(
  impression : ImpressionModel,
  bidFailedInfoModel: BidFailedInfoModel,
  deal : Double
) {
  lazy val hasAnyDeal : Boolean = deal > 0
}
