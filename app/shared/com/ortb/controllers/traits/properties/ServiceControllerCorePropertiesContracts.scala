package shared.com.ortb.controllers.traits.properties

import shared.com.ortb.controllers.implementations.WebApiResultImplementation
import play.api.Logger
import shared.com.ortb.manager.AppManager
import shared.com.ortb.model.config.{ ConfigModel, DemandSidePlatformConfigurationModel, ServiceBaseModel, ServiceModel, ServicesModel }
import shared.io.loggers.DatabaseLogTracer

trait ServiceControllerCorePropertiesContracts {
  val config : ConfigModel
  val services : ServicesModel
  val selfProperties : ServiceControllerCorePropertiesContracts
  val currentServiceModel : ServiceBaseModel
  val appManager : AppManager
  val webApiResult : WebApiResultImplementation
  val serviceTitle : String
  val logger : Logger
  val databaseLogger : DatabaseLogTracer
}
