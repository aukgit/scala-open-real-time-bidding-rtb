package shared.com.ortb.model.config

case class ServerInfoModel(
  commonDomain : String,
  services : ServicesModel,
  isUseDefaultDomainForAll : Boolean,
  isStaticSimulate : Boolean,
  demandSidePlatformConfiguration : DemandSidePlatformConfigurationModel,
  requestDefaultHeaders : Array[KeyValuePairModel],
  redisServer : DomainPortModel
)

case class DemandSidePlatformConfigurationModel(
  defaultBidIncrementNumber : Double,
  defaultBidStaticDeal : Double
)