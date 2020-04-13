package com.ortb.persistent.repositoryPattern

import scala.concurrent.ExecutionContext
import com.ortb.persistent.repositoryPattern.traits.{EntityResponseCreator, SingleRepositoryBase, DatabaseActionExecutor}

import scala.concurrent.duration._
import com.ortb.model.config.ConfigModel
import com.ortb.manager.AppManager
import com.ortb.persistent.schema.DatabaseSchema
import io.traits.FutureToRegular
import slick.lifted.AbstractTable

abstract class Repository[TTable <: AbstractTable[_], TRow <: Null, TKey]
(appManager: AppManager)
  extends
    DatabaseSchema(appManager) with
    SingleRepositoryBase[TTable, TRow, TKey] {

  lazy protected implicit val executionContext: ExecutionContext = appManager.executionContextManager.createDefault().prepare()
  lazy protected val config: ConfigModel = appManager.config
  /**
   * default timeout from config, if < 0 then infinite
   */
  lazy protected implicit val defaultTimeout: Duration = if (config.defaultTimeout < 0) Duration.Inf else config.defaultTimeout.seconds
  lazy protected val isLogQueries: Boolean = config.isLogDatabaseQueryLogs;
}
