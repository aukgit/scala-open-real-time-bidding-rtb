package shared.com.ortb.model

import shared.com.ortb.importedModels.biddingRequests.ImpressionModel

case class ImpressionBiddableInfoModel(
  impression: ImpressionModel,
  advertises: Array[AdvertiseRow],
  attributes: ImpressionBiddableAttributesModel
)
