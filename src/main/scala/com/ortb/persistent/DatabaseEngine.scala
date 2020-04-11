package com.ortb.persistent

import java.net.URI

import com.ortb.manager.AppManager
import io.PathHelper
import slick.jdbc.SQLiteProfile
import slick.jdbc.SQLiteProfile.api._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Await
import scala.concurrent.duration._

class DatabaseEngine(appManager: AppManager) {
  val databasePath: String = PathHelper.getResourceFileAbsolutePath(appManager.config.databaseRelativePath)
  val db = Database.forConfig(databasePath)
}


