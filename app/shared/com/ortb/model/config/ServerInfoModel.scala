package shared.com.ortb.model.config

case class ServerInfoModel(
  globalDomainHost : String,
  prefixRouting : String,
  services : ServicesModel,
  isUseDefaultDomainForAll : Boolean,
  demandSidePlatformConfiguration : DemandSidePlatformConfigurationModel,
  requestDefaultHeaders : Array[KeyValuePairModel],
  redisServer : DomainPortModel
)
