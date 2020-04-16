package com.ortb.model.config

case class ServicesModel(
  root                      : ServiceModel,
  requestSimulatorService   : ServiceModel,
  monitorService            : ServiceModel,
  exchangeService           : ServiceModel,
  advertiserService         : ServiceModel,
  bidderService             : ServiceModel,
  preCachedBidService       : ServiceModel,
  supplySidePlatformService : ServiceModel,
  demandSidePlatForms       : Array[ServiceModel]
)
