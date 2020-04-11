package com.ortb.manager

import com.ortb.constants.AppConstants
import com.ortb.model.config.ConfigModel
import com.ortb.persistent.DatabaseEngine
import io.sentry.Sentry

trait AppManagerBase {
  Sentry.init(AppConstants.SentryDSN)
  lazy val ConfigManager = new ConfigurationManager()
  lazy val config: ConfigModel = ConfigManager.getConfig(AppConstants.PathConstants.ConfigDefaultPath)
}

class AppManager extends AppManagerBase {
  lazy val databaseEngine: DatabaseEngine = new DatabaseEngine(this)
}
