package shared.com.ortb.controllers.traits.properties

import shared.com.ortb.controllers.implementations.WebApiResponseImplementation
import play.api.Logger
import shared.com.ortb.manager.AppManager
import shared.com.ortb.model.config.core.ServiceBaseModel
import shared.com.ortb.model.config.{ ConfigModel, DemandSidePlatformConfigurationModel, ServiceModel, ServicesModel }
import shared.io.loggers.DatabaseLogTracer

trait ServiceControllerCorePropertiesContracts {
  val config : ConfigModel
  val services : ServicesModel
  val selfProperties : ServiceControllerCorePropertiesContracts
  val currentServiceModel : ServiceBaseModel
  val appManager : AppManager
  val webApiResponse : WebApiResponseImplementation
  val serviceTitle : String
  val logger : Logger
  val databaseLogger : DatabaseLogTracer
}
