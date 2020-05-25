package shared.com.ortb.model.config

/**
 *
 * @param isDebug                                   : Debug enabled.
 * @param isLogError                                : Log on error
 * @param isLogDatabaseQueryLogs                    : Log repository queries
 * @param isLogDatabaseActionsToDatabase            : Log repository Create, Update, Delete actions to logtrace database table.
 * @param isLogToDatabaseTrace                      : If enabled then Iterable.logToDatabaseAsJson will log to database or else ignore
 * @param isPrintDuringLogDatabaseActionsToDatabase : Print database logs to console
 * @param isEnableSentry                            : Puts logs on sentry
 */
case class LogConfigurationModel(
  isDebug : Boolean,
  isLogError : Boolean,
  isLogDatabaseQueryLogs : Boolean,
  isLogDatabaseActionsToDatabase : Boolean,
  isLogToDatabaseTrace : Boolean,
  isPrintDuringLogDatabaseActionsToDatabase : Boolean,
  isEnableSentry : Boolean
)
