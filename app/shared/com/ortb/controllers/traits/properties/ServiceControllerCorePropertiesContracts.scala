package shared.com.ortb.controllers.traits.properties

import shared.com.ortb.controllers.implementations.RestWebApiOkImplementation
import play.api.Logger
import shared.com.ortb.manager.AppManager
import shared.com.ortb.model.config.{ ConfigModel, DemandSidePlatformConfigurationModel, ServiceModel, ServicesModel }
import shared.io.loggers.DatabaseLogTracer

trait ServiceControllerCorePropertiesContracts {
  val config : ConfigModel
  val services : ServicesModel
  val selfProperties : ServiceControllerCorePropertiesContracts
  val currentServiceModel : ServiceModel
  val appManager : AppManager
  val restWebApiOkJson : RestWebApiOkImplementation
  val serviceTitle : String
  val logger : Logger
  val databaseLogger : DatabaseLogTracer
}
