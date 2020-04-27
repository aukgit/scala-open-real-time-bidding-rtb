package shared.com.ortb.model.config

case class ServerInfoModel(
  commonDomain : String,
  services : ServicesModel,
  isUseDefaultDomainForAll : Boolean,
  demandSidePlatformConfiguration : DemandSidePlatformConfigurationModel,
  requestDefaultHeaders : Array[KeyValuePairModel],
  redisServer : DomainPortModel
)
