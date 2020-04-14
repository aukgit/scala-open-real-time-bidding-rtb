package com.ortb.persistent.repositoryPattern.traits

import java.awt.dnd.InvalidDnDOperationException

import com.ortb.enumeration.DatabaseActionType.DatabaseActionType
import slick.lifted.{AbstractTable, TableQuery}

import scala.concurrent.{Future, Await}
import slick.dbio.{NoStream, DBIOAction, Effect}
import slick.sql._
import io.AppLogger
import com.ortb.model.results.RepositoryOperationResult
import com.ortb.persistent.repositoryPattern.Repository

trait DatabaseActionExecutor[TTable, TRow, TKey] {
  this: Repository[TTable, TRow, TKey] =>

  protected def getRunResult[T](dbAction: T): Option[Future[Seq[TRow]]] = {
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
}
