package com.ortb.manager

import com.ortb.manager.traits.AppManagerBaseType
import com.ortb.persistent.DatabaseEngineManager
import slick.jdbc.SQLiteProfile

class AppManager extends AppManagerBaseType {
  lazy val databaseEngine: DatabaseEngineManager = new DatabaseEngineManager(this)
  def getDb: SQLiteProfile.backend.DatabaseDef = databaseEngine.db
}
