package com.ortb.manager

import com.ortb.constants.AppConstants
import com.ortb.model.config.ConfigModel

trait AppManagerType {
  lazy val ConfigManager = new ConfigurationManager()
  lazy val config: ConfigModel = ConfigManager.getConfig(AppConstants.PathConstants.ConfigDefaultPath)
}

class AppManager extends AppManagerType {

}
