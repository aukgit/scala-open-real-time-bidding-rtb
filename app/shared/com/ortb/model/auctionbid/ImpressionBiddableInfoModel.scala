package shared.com.ortb.model.auctionbid

import shared.com.ortb.model.auctionbid.biddingRequests.ImpressionModel
import shared.com.ortb.persistent.schema.Tables._

case class ImpressionBiddableInfoModel(
  impression : ImpressionModel,
  advertisesWithLimit : Option[Seq[AdvertiseRow]],
  attributes : ImpressionBiddableAttributesModel
)
