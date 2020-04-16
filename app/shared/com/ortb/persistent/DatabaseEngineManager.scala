package shared.com.ortb.persistent

import shared.com.ortb.manager.AppManager
import shared.com.ortb.model.config.DatabaseGenerateConfigModel
import shared.com.ortb.persistent.schema.DatabaseSchema
import shared.io.PathHelper
import slick.jdbc.SQLiteProfile.api._

class DatabaseEngineManager(appManager: AppManager) {
  lazy val databasePath: String = PathHelper.getResourceFileAbsolutePath(
    appManager.config.databaseRelativePath
  )

  lazy val databaseEngineCodeGenerator: DatabaseGenerateConfigModel =
    appManager.config.databaseGenerate
  lazy val databaseUrl: String = databaseEngineCodeGenerator.compiledDatabaseUrl
  println( databaseUrl)
  println( appManager.config)
  lazy val db = Database.forURL(url = databaseUrl)
  lazy val databaseSchema = new DatabaseSchema(appManager)
}
