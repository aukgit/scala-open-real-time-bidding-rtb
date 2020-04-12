package com.ortb.persistent.repositoryPattern

import scala.concurrent.{Future, ExecutionContext, ExecutionContextExecutor}
import com.ortb.persistent.repositoryPattern.traits.{EntityResponseCreator, SingleRepositoryBase}

import scala.concurrent.duration._
import com.ortb.model.config.ConfigModel
import com.ortb.manager.AppManager
import com.ortb.model.results.RepositoryOperationResult
import io.AppLogger
import slick.dbio.{NoStream, Effect}
import slick.lifted.{Rep, TableQuery}
import slick.profile
import slick.sql.FixedSqlAction

abstract class Repository[TTable, TRow >: null, TKey](appManager: AppManager)
  extends SingleRepositoryBase[TTable, TRow, TKey] with EntityResponseCreator[TRow] {

  lazy protected implicit val executionContext: ExecutionContext = appManager.executionContextManager.createDefault().prepare()
  lazy protected val config: ConfigModel = appManager.config
  /**
   * default timeout from config, if < 0 then infinite
   */
  lazy protected implicit val defaultTimeout: Duration = if (config.defaultTimeout < 0) Duration.Inf else config.defaultTimeout.seconds
  lazy protected val isLogQueries: Boolean = config.isLogDatabaseQueryLogs;

  protected def executeDbActionAsync(
    entityKey: TKey,
    entity: TRow,
    dbAction: FixedSqlAction[Int, NoStream, Effect.Write],
    isPerformActionOnExist: Boolean
  ): Future[RepositoryOperationResult[TRow]] = {
    val isPerform = isExists(entityKey) == isPerformActionOnExist
    if (isPerform) {
      executeDbActionAsync(entity, dbAction = dbAction)
    }

    getEmptyResponse
  }

  private def getEmptyResponse = {
    AppLogger.info(isLogQueries, s"Operation Skipped.")

    Future {
      emptyResponse
    }
  }

  protected def executeDbActionAsync(
    entity: TRow,
    dbAction: FixedSqlAction[Int, NoStream, Effect.Write],
  ): Future[RepositoryOperationResult[TRow]] = {
    try {
      return db.run(dbAction).map(r => createResponseForAffectedRow(r, entity))
    }
    catch {
      case e: Exception => AppLogger.error(e)
    }

    getEmptyResponse

  }
}


