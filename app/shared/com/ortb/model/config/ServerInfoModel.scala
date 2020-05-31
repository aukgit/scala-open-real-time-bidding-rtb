package shared.com.ortb.model.config

case class ServerInfoModel(
  globalDomainHost : String,
  services : ServicesModel,
  isUseDefaultDomainForAll : Boolean,
  demandSidePlatformConfiguration : DemandSidePlatformConfigurationModel,
  requestDefaultHeaders : Array[KeyValuePairModel],
  redisServer : DomainPortModel
)
