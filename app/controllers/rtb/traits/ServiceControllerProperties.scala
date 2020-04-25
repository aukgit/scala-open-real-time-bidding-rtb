package controllers.rtb.traits

import controllers.rtb.implementations.RestWebApiOkJsonImplementation
import shared.com.ortb.manager.AppManager
import shared.com.ortb.model.config.{ ConfigModel, ServiceModel }

trait ServiceControllerProperties {
  val appManager : AppManager
  val restWebApiOkJson : RestWebApiOkJsonImplementation
  val config : ConfigModel
  val serviceModel : ServiceModel
  val serviceTitle : String
}
