package controllers.rtb

import shared.com.ortb.manager.AppManager
import shared.com.ortb.model.config.{ ConfigModel, ServiceModel }

class ServiceControllerPropertiesImplementation
(val serviceBaseApiController : ServiceBaseApiController)
  extends ServiceControllerProperties {
  lazy val restWebApiOkJson = new RestWebApiOkJsonImplementation(serviceBaseApiController)
  lazy val config : ConfigModel = appManager.config
  lazy val serviceModel : ServiceModel = config.server.services.requestSimulatorService
  lazy val serviceTitle : String = serviceModel.title
  override val appManager : AppManager = serviceBaseApiController.appManager
}
