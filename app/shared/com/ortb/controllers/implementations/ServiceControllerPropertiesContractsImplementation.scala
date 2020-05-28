package shared.com.ortb.controllers.implementations

import shared.com.ortb.controllers.core.ServiceBaseApiController
import shared.com.ortb.controllers.traits.properties.ServiceControllerCorePropertiesContracts
import play.api.Logger
import shared.com.ortb.manager.AppManager
import shared.com.ortb.model.config.core.ServiceBaseModel
import shared.com.ortb.model.config.{ ConfigModel, ServiceModel, ServicesModel }
import shared.io.helpers.EmptyValidateHelper
import shared.io.loggers.{ DatabaseLogTracer, DatabaseLogTracerImplementation }

class ServiceControllerPropertiesContractsImplementation
(val serviceBaseApiController : ServiceBaseApiController, val serviceModelInstance : ServiceBaseModel)
  extends ServiceControllerCorePropertiesContracts {
  EmptyValidateHelper.throwOnNullOrNoneOrNil(serviceModelInstance)
  lazy val webApiResult = new WebApiResultImplementation(serviceBaseApiController)
  lazy val config : ConfigModel = appManager.config
  lazy val serviceTitle : String = serviceModelInstance.title
  lazy override val appManager : AppManager = serviceBaseApiController.appManager
  lazy override val services : ServicesModel = config.server.services
  lazy override val selfProperties : ServiceControllerCorePropertiesContracts = this
  lazy override val currentServiceModel : ServiceBaseModel = serviceModelInstance
  lazy override val logger : Logger = serviceBaseApiController.logger
  lazy override val databaseLogger : DatabaseLogTracer = new DatabaseLogTracerImplementation(
    appManager,
    this.getClass.getName)
}
