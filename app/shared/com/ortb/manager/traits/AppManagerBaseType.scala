package shared.com.ortb.manager.traits

import shared.com.ortb.manager.ConfigurationManager
import shared.com.ortb.constants.AppConstants
import shared.com.ortb.model.config.ConfigModel

trait AppManagerBaseType {
  lazy val ConfigManager        = new ConfigurationManager
  lazy val config : ConfigModel = ConfigManager
    .getConfig(AppConstants.PathConstants.ConfigDefaultPath)
}
