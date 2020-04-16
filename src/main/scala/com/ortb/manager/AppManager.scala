package com.ortb.manager

import com.ortb.constants.AppConstants
import com.ortb.manager.traits.AppManagerBaseType
import com.ortb.persistent.DatabaseEngineManager
import io.sentry.Sentry
import slick.jdbc.SQLiteProfile

class AppManager extends AppManagerBaseType {
  lazy val databaseEngine : DatabaseEngineManager = new DatabaseEngineManager(this)
  lazy val executionContextManager                = new ExecutionContextManager(this)

  def getDb : SQLiteProfile.backend.DatabaseDef = databaseEngine.db

  if (config.isEnableSentry) {
    Sentry.init(AppConstants.SentryDSN)
  }
}
