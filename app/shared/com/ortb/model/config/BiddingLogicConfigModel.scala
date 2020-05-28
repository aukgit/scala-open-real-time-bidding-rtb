package shared.com.ortb.model.config

import shared.com.ortb.model.config.core.{ BidPriceConfigBaseModel, BiddingLogicBaseConfigModel }

case class BiddingLogicConfigModel(
  staticPrice : BidPriceConfigBaseModel,
  dynamicPrice : BidPriceConfigBaseModel
) extends BiddingLogicBaseConfigModel
