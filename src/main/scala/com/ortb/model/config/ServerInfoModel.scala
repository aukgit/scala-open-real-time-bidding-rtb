package com.ortb.model.config

case class ServerInfoModel(
  commonDomain: String,
  services: ServicesModel,
  isUseDefaultDomainForAll: Boolean,
  requestDefaultHeaders: Array[KeyValuePairModel],
  redisServer: DomainPortModel
)
