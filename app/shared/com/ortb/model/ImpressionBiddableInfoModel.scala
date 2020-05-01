package shared.com.ortb.model

import shared.com.ortb.model.auctionbid.biddingRequests.ImpressionModel
import shared.com.ortb.persistent.schema.Tables._

case class ImpressionBiddableInfoModel(
  impression: ImpressionModel,
  advertises: Option[Array[AdvertiseRow]],
  exactHeightWidthAdvertises: Option[Seq[AdvertiseRow]],
  attributes: ImpressionBiddableAttributesModel
)
