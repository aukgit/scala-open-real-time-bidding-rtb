package com.ortb.persistent.repositoryPattern

import java.awt.dnd.InvalidDnDOperationException

import com.ortb.enumeration.DatabaseActionType.DatabaseActionType

import scala.concurrent.{Future, ExecutionContext, Await}
import com.ortb.persistent.repositoryPattern.traits._

import scala.concurrent.duration._
import com.ortb.model.config.ConfigModel
import com.ortb.manager.AppManager
import com.ortb.model.results.RepositoryOperationResult
import com.ortb.persistent.schema.DatabaseSchema
import io.AppLogger
import slick.dbio.{NoStream, Effect, DBIOAction}
import slick.lifted.AbstractTable
import slick.sql.{FixedSqlAction, SqlStreamingAction, FixedSqlStreamingAction}

abstract class Repository[TTable <: AbstractTable[_], TRow <: Null, TKey]
(appManager: AppManager)
  extends
    DatabaseSchema(appManager) with
    SingleRepositoryBase[TTable, TRow, TKey] with
    EntityResponseCreator[TTable, TRow, TKey] with
    DatabaseActionExecutor [TTable, TRow, TKey]{

  lazy protected implicit val executionContext: ExecutionContext = appManager.executionContextManager.createDefault().prepare()
  lazy protected val config: ConfigModel = appManager.config
  /**
   * default timeout from config, if < 0 then infinite
   */
  lazy protected implicit val defaultTimeout: Duration = if (config.defaultTimeout < 0) Duration.Inf else config.defaultTimeout.seconds
  lazy protected val isLogQueries: Boolean = config.isLogDatabaseQueryLogs

}
