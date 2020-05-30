package shared.com.ortb.serverAkka.framework.sampleCodes

import shared.com.ortb.constants.AppConstants
import shared.com.ortb.manager.AppManager
import shared.com.ortb.model.config.core.ServiceBaseModel
import shared.com.ortb.model.config.{ ConfigModel, ServerInfoModel }

trait ServiceProperties {
  lazy val appManager : AppManager = AppConstants.AppManager
  lazy val config : ConfigModel = appManager.config
  lazy val serverConfig : ServerInfoModel = config.server
  lazy val endPointPrefixes : String = serviceModel.prefixRouting
  val serviceModel : ServiceBaseModel
}
