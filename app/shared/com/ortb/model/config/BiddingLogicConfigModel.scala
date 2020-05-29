package shared.com.ortb.model.config

import shared.com.ortb.model.config.core.BiddingLogicBaseConfigModel

case class BiddingLogicConfigModel(
  randomSumOfRanges : Array[String],
  staticPrice : BidPriceConfigModel,
  dynamicPrice : BidPriceConfigModel
) extends BiddingLogicBaseConfigModel
