package shared.com.ortb.model.config

import shared.com.ortb.model.config.core.BiddingLogicBaseConfigModel

case class BiddingLogicConfigModel(
  staticPrice : BidPriceConfigModel,
  dynamicPrice : BidPriceConfigModel
) extends BiddingLogicBaseConfigModel
