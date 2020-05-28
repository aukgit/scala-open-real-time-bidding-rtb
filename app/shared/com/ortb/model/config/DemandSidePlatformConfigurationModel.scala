package shared.com.ortb.model.config

import shared.com.ortb.model.ranges.RangeDoubleModel

case class DemandSidePlatformConfigurationModel(
  defaultBidIncrementNumber : Double,
  defaultBidStaticDeal : Double,
  isStaticSimulate : Boolean,
  defaultAdvertiseLimit : Int,
  defaultGenericLimit : Int,
  isAddNewAdvertiseOnNotFound : Boolean,
  isRedisCacheEnabled : Boolean,
  globalRandomRange : RangeDoubleModel,
  biddingLogicConfig : BiddingLogicConfigModel
)
