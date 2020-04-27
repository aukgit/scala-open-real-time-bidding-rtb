package shared.com.ortb.model.config

case class DemandSidePlatformConfigurationModel(
  defaultBidIncrementNumber : Double,
  defaultBidStaticDeal : Double,
  isStaticSimulate : Boolean,
  isAddNewAdvertiseOnNotFound : Boolean
)
