package shared.com.ortb.model.config

case class LogConfigurationModel(
  isDebug : Boolean,
  isLogError : Boolean,
  isLogDatabaseQueryLogs : Boolean,
  isLogDatabaseActionsToDatabase : Boolean,
  isPrintDuringLogDatabaseActionsToDatabase : Boolean,
  isEnableSentry : Boolean
)
