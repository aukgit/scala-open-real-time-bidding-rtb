package shared.com.ortb.model.auctionbid

import shared.com.ortb.persistent.schema.Tables._
import shared.com.ortb.model.auctionbid.biddingRequests.ImpressionModel

case class ImpressionBiddableInfoModel(
  impression: ImpressionModel,
  advertises: Option[Array[AdvertiseRow]],
  exactHeightWidthAdvertises: Option[Seq[AdvertiseRow]],
  attributes: ImpressionBiddableAttributesModel
)
