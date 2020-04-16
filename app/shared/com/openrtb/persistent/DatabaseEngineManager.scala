package com.ortb.persistent

import com.ortb.manager.AppManager
import com.ortb.model.config.DatabaseGenerateConfigModel
import com.ortb.persistent.schema.DatabaseSchema
import io.PathHelper
import slick.jdbc.SQLiteProfile.api._

class DatabaseEngineManager(appManager: AppManager) {
  lazy val databasePath: String = PathHelper.getResourceFileAbsolutePath(
    appManager.config.databaseRelativePath
  )

  lazy val databaseEngineCodeGenerator: DatabaseGenerateConfigModel =
    appManager.config.databaseGenerate
  lazy val databaseUrl: String = databaseEngineCodeGenerator.compiledDatabaseUrl
  lazy val db = Database.forURL(url = databaseUrl)
  lazy val databaseSchema = new DatabaseSchema(appManager)
}
