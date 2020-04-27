package controllers.rtb.implementations

import controllers.rtb.core.ServiceBaseApiController
import controllers.rtb.traits.properties.ServiceControllerCoreProperties
import play.api.Logger
import shared.com.ortb.manager.AppManager
import shared.com.ortb.model.config.{ ConfigModel, ServiceModel, ServicesModel }
import shared.io.helpers.EmptyValidateHelper


class ServiceControllerPropertiesImplementation
(val serviceBaseApiController : ServiceBaseApiController, val serviceModelInstance : ServiceModel)
  extends ServiceControllerCoreProperties {
  EmptyValidateHelper.throwOnNullOrNone(serviceModelInstance)
  lazy val restWebApiOkJson = new RestWebApiOkJsonImplementation(serviceBaseApiController)
  lazy val config : ConfigModel = appManager.config
  lazy val serviceTitle : String = serviceModelInstance.title
  lazy override val appManager : AppManager = serviceBaseApiController.appManager
  lazy override val services : ServicesModel = config.server.services
  lazy override val selfProperties : ServiceControllerCoreProperties = this
  lazy override val currentServiceModel : ServiceModel = serviceModelInstance
  lazy override val logger : Logger = serviceBaseApiController.logger
}
