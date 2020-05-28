package shared.com.ortb.model.config.core

import shared.com.ortb.model.config.BidPriceConfigModel

abstract class BiddingLogicBaseConfigModel {
  val staticPrice : BidPriceConfigModel
  val dynamicPrice : BidPriceConfigModel
}
