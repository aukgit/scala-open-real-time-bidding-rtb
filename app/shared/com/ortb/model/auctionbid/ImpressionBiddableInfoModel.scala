package shared.com.ortb.model.auctionbid

import shared.com.ortb.model.auctionbid.biddingRequests.ImpressionModel
import shared.com.ortb.persistent.schema.Tables._

case class ImpressionBiddableInfoModel(
  impression : ImpressionModel,
  advertises : Option[Seq[AdvertiseRow]],
  exactHeightWidthAdvertises : Option[Seq[AdvertiseRow]],
  attributes : ImpressionBiddableAttributesModel
)
