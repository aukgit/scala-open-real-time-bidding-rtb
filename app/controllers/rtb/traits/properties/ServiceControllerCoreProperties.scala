package controllers.rtb.traits.properties

import controllers.rtb.implementations.RestWebApiOkJsonImplementation
import play.api.Logger
import shared.com.ortb.manager.AppManager
import shared.com.ortb.model.config.{ ConfigModel, ServiceModel, ServicesModel }

trait ServiceControllerCoreProperties {
  val config : ConfigModel
  val services : ServicesModel
  val selfProperties : ServiceControllerCoreProperties
  val currentServiceModel : ServiceModel
  val appManager : AppManager
  val restWebApiOkJson : RestWebApiOkJsonImplementation
  val serviceModel : ServiceModel
  val serviceTitle : String
  protected val logger : Logger
}
