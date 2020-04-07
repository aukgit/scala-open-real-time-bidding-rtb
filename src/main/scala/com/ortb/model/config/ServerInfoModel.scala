package com.ortb.model.config

case class ServerInfoModel(
  commonDomain: String,
  services: List[ServiceModel],
  isUseDefaultDomainForAll: Boolean,
  requestDefaultHeaders: List[KeyValuePairModel],
  redisServer: DomainPortModel
)
