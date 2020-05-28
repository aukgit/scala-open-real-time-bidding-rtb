package shared.com.ortb.model.config

import shared.com.ortb.model.config.core.BidPriceConfigBaseModel

case class BidPriceConfigModel(
 bidFloorExistLogic : Array[String],
 bidFloorNonExistLogic : Array[String]
) extends BidPriceConfigBaseModel
