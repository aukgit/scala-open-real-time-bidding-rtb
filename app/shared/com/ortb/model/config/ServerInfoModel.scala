package shared.com.ortb.model.config

case class ServerInfoModel(
  commonDomain             : String,
  services                 : ServicesModel,
  isUseDefaultDomainForAll : Boolean,
  isStaticSimulate         : Boolean,
  requestDefaultHeaders    : Array[KeyValuePairModel],
  redisServer              : DomainPortModel
)
