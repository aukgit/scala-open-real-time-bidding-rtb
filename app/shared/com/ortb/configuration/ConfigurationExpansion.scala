package shared.com.ortb.configuration

import shared.com.ortb.manager.AppManager
import shared.com.ortb.model.config.{ ConfigModel, LogConfigurationModel }

trait ConfigurationExpansion {
  lazy val config : ConfigModel = appManager.config
  lazy val logConfiguration : LogConfigurationModel = config.logConfiguration
  /**
   * Determinate weather to log queries or not.
   */
  lazy val isLogDatabaseQueryLogs : Boolean = logConfiguration.isLogDatabaseQueryLogs
  /**
   * Determinate weather to log queries to database or not
   */
  lazy val isLogDatabaseActionsToDatabase : Boolean = logConfiguration.isLogDatabaseActionsToDatabase
  val appManager : AppManager
}
