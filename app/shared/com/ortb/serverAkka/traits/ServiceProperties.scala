package shared.com.ortb.serverAkka.traits

import shared.com.ortb.constants.AppConstants
import shared.com.ortb.manager.AppManager
import shared.com.ortb.model.config.core.ServiceBaseModel
import shared.com.ortb.model.config.{ ConfigModel, ServerInfoModel, ServicesModel }
import shared.io.extensions.TypeConvertExtensions._

trait ServiceProperties {
  lazy val appManager : AppManager = AppConstants.AppManager
  lazy val config : ConfigModel = appManager.config
  lazy val serverConfig : ServerInfoModel = config.server
  lazy val services : ServicesModel = serverConfig.services
  lazy val endPointPrefixes : String = currentServiceModel.prefixRouting
  lazy val globalDomainHost : String = serverConfig.globalDomainHost
  lazy val servicePort : Int = currentServiceModel.port
  lazy val finalDomainHost : String = currentServiceModel
    .domainHost
    .getOrElseDefault(globalDomainHost)

  val currentServiceModel : ServiceBaseModel
}


