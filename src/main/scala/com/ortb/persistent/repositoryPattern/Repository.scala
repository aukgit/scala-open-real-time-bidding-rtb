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
    EntityResponseCreator[TTable, TRow, TKey] {

  lazy protected implicit val executionContext: ExecutionContext = appManager.executionContextManager.createDefault().prepare()
  lazy protected val config: ConfigModel = appManager.config
  /**
   * default timeout from config, if < 0 then infinite
   */
  lazy protected implicit val defaultTimeout: Duration = if (config.defaultTimeout < 0) Duration.Inf else config.defaultTimeout.seconds
  lazy protected val isLogQueries: Boolean = config.isLogDatabaseQueryLogs

  protected def saveAsync(
                           entityKey: TKey,
                           entity: TRow,
                           dbAction: FixedSqlAction[Int, NoStream, Effect.Write],
                           isPerformActionOnExist: Boolean,
                           actionType: DatabaseActionType
                         ): Future[RepositoryOperationResult[TRow]] = {
    val isPerform = this.isExists(entityKey) == isPerformActionOnExist
    if (isPerform) {
      return saveAsync(entity, dbAction = dbAction, actionType)
    }

    getEmptyResponse
  }

  protected def saveAsync(
                           entity: TRow,
                           dbAction: FixedSqlAction[Int, NoStream, Effect.Write],
                           actionType: DatabaseActionType
                         ): Future[RepositoryOperationResult[TRow]] = {
    try {
      return db.run(dbAction).map(r => createResponseForAffectedRow(r, entity))
    }
    catch {
      case e: Exception => AppLogger.error(e, s"Failed at performing $actionType")
    }

    getEmptyResponse
  }


  /**
   * Runs db.run(..) and returns the non async result.
   *
   * @param dbAction
   * @tparam T
   *
   * @return
   */
  protected def run[T](dbAction: T): Seq[TRow] = {
    Await.result(this.runAsync(dbAction), defaultTimeout)
  }

  /**
   * runs db.run(..)
   *
   * @param dbAction
   * @tparam T
   *
   * @return
   */
  protected def runAsync[T](dbAction: T): Future[Seq[TRow]] = {
    try {
      val results = getRunResult(dbAction).get
      AppLogger.logEntities(isLogQueries, results)
      return results
    }
    catch {
      case e: Exception => AppLogger.error(e)
    }

    null
  }

  private def getRunResult[T](dbAction: T): Option[Future[Seq[TRow]]] = {
    dbAction match {
      case fixedSql: FixedSqlAction[Seq[TRow], NoStream, Effect.All]
      =>
        Some(db.run(fixedSql))
      case fixedSqlStreaming: FixedSqlStreamingAction[Seq[TRow], NoStream, Effect.All] =>
        Some(db.run(fixedSqlStreaming))
      case sqlStreaming: SqlStreamingAction[Seq[TRow], NoStream, Effect.All] =>
        Some(db.run(sqlStreaming))
      case dbAction2: DBIOAction[Seq[TRow], NoStream, Effect.All] =>
        Some(db.run(dbAction2))
      case _ =>
        throw new InvalidDnDOperationException("Invalid operation for runAsync.")
    }
  }
}
