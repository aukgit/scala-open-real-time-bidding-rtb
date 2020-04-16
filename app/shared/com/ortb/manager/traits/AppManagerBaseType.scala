package com.ortb.manager.traits

import com.ortb.manager.ConfigurationManager
import com.ortb.constants.AppConstants
import com.ortb.model.config.ConfigModel

trait AppManagerBaseType {
  lazy val ConfigManager        = new ConfigurationManager
  lazy val config : ConfigModel = ConfigManager
    .getConfig(AppConstants.PathConstants.ConfigDefaultPath)
}
