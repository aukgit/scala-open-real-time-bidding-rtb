package controllers.rtb

import shared.com.ortb.importedModels.biddingRequests.ImpressionModel
import shared.com.ortb.persistent.schema.Tables._

case class ImpressionBiddableInfoModel(
  impression: ImpressionModel,
  advertises: Array[AdvertiseRow],
  attributes: ImpressionBiddableAttributesModel
)
