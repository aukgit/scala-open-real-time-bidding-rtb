package shared.com.ortb.manager

import shared.com.ortb.constants.AppConstants
import shared.com.ortb.manager.traits.AppManagerBaseType
import shared.com.ortb.persistent.DatabaseEngineManager
import io.sentry.Sentry
import javax.inject.Singleton
import slick.jdbc.SQLiteProfile

@Singleton
class AppManager extends AppManagerBaseType {
  lazy val databaseEngine : DatabaseEngineManager = new DatabaseEngineManager(this)
  lazy val executionContextManager                = new ExecutionContextManager(this)

  lazy val db : SQLiteProfile.backend.DatabaseDef = databaseEngine.db

  if (config.logConfiguration.isEnableSentry) {
    Sentry.init(AppConstants.SentryDSN)
  }
}
