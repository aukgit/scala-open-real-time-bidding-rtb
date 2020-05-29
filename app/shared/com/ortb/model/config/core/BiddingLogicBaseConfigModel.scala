package shared.com.ortb.model.config.core

import shared.com.ortb.model.config.BidPriceConfigModel

abstract class BiddingLogicBaseConfigModel {
  val randomSumOfRanges : Array[String]
  val staticPrice : BidPriceConfigModel
  val dynamicPrice : BidPriceConfigModel
}
