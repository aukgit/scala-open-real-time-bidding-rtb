package shared.com.ortb.model.config

case class DemandSidePlatformConfigurationModel(
  defaultBidIncrementNumber : Double,
  defaultBidStaticDeal : Double,
  isStaticSimulate : Boolean,
  defaultAdvertiseLimit: Int,
  defaultGenericLimit: Int,
  isAddNewAdvertiseOnNotFound : Boolean,
  isRedisCacheEnabled : Boolean
)
