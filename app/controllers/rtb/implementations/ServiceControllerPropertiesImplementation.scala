package controllers.rtb.implementations

import controllers.rtb.ServiceBaseApiController
import controllers.rtb.traits.ServiceControllerProperties
import shared.com.ortb.manager.AppManager
import shared.com.ortb.model.config.{ ConfigModel, ServiceModel }
import shared.io.helpers.EmptyValidateHelper


class ServiceControllerPropertiesImplementation
(val serviceBaseApiController : ServiceBaseApiController, val serviceModelInstance : ServiceModel)
  extends ServiceControllerProperties {
  EmptyValidateHelper.throwOnNullOrNone(serviceModelInstance)
  lazy val restWebApiOkJson = new RestWebApiOkJsonImplementation(serviceBaseApiController)
  lazy val config : ConfigModel = appManager.config
  lazy val serviceModel : ServiceModel = serviceModelInstance
  lazy val serviceTitle : String = serviceModel.title
  override val appManager : AppManager = serviceBaseApiController.appManager
}
