package com.ortb.persistent

import java.net.URI

import com.ortb.manager.AppManager
import com.ortb.model.config.DatabaseGenerateConfigModel
import com.ortb.persistent.schema.DatabaseSchema
import io.{PathHelper, AppLogger}
import org.sqlite._
import slick.dbio.{NoStream, DBIOAction}
import slick.jdbc.SQLiteProfile
import slick.jdbc.SQLiteProfile.api._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Await, Future}
import scala.concurrent.duration._

class DatabaseEngine(appManager: AppManager) {
  lazy val databasePath: String = PathHelper.getResourceFileAbsolutePath(appManager.config.databaseRelativePath)
  lazy val databaseEngineCodeGenerator: DatabaseGenerateConfigModel = appManager.config.databaseGenerate
  lazy val databaseUrl: String = databaseEngineCodeGenerator.compiledDatabaseUrl
  lazy val db = Database.forURL(url = databaseUrl)
  lazy val databaseSchema = new DatabaseSchema(appManager)
}


