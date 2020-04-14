package com.ortb.persistent.repositoryPattern.traits

import java.awt.dnd.InvalidDnDOperationException

import com.ortb.enumeration.DatabaseActionType.DatabaseActionType
import com.ortb.implicits.ImplicitsDefinitions.anyRefCaller

import scala.concurrent.{Future, Await}
import slick.dbio.{NoStream, DBIOAction, Effect}
import slick.sql._
import io.AppLogger
import com.ortb.model.results.RepositoryOperationResult
import com.ortb.persistent.repositoryPattern.Repository

trait DatabaseActionExecutor[TTable, TRow, TKey] {
  this : Repository[TTable, TRow, TKey] =>

  def quickSaveAsync(
    dbAction : FixedSqlAction[Int, NoStream, Effect.Write],
    actionType : DatabaseActionType
  ) : Future[RepositoryOperationResult[TRow, TKey]] = {
    try {
      return db.run(dbAction).map((affectedRowsCount : Int) =>
                                    createResponseForAffectedRowCount(
                                      affectedRow = affectedRowsCount,
                                      entity = None,
                                      actionType = actionType))
    }
    catch {
      case e : Exception => AppLogger.error(e, s"Failed at performing $actionType")
    }

    getEmptyResponse(actionType)
  }

  def saveAsync(
    entityKey : TKey,
    entity : TRow,
    dbAction : FixedSqlAction[Int, NoStream, Effect.Write],
    isPerformActionOnExist : Boolean,
    actionType : DatabaseActionType
  ) : Future[RepositoryOperationResult[TRow, TKey]] = {
    val isPerform = this.isExists(entityKey) == isPerformActionOnExist
    if (isPerform) {
      return saveAsync(Some(entity), dbAction = dbAction, actionType)
    }

    getEmptyResponse(actionType)
  }

  def saveAsync(
    entity : Option[TRow],
    dbAction : FixedSqlAction[Int, NoStream, Effect.Write],
    actionType : DatabaseActionType
  ) : Future[RepositoryOperationResult[TRow, TKey]] = {
    try {
      return db.run(dbAction).map((affectedRowsCount : Int) =>
                                    createResponseForAffectedRowCount(
                                      affectedRowsCount,
                                      entity,
                                      actionType))
    }
    catch {
      case e : Exception => AppLogger.error(e, s"Failed at performing $actionType")
    }

    getEmptyResponse(actionType)
  }

  def saveAsync(
    dbAction : FixedSqlAction[TRow, NoStream, Effect.Write],
    actionType : DatabaseActionType
  ) : Future[RepositoryOperationResult[TRow, TKey]] = {
    try {
      return db.run(dbAction).map((entity : TRow) =>
                                    this.createResponseForAffectedRow(
                                      affectedEntity = Some(entity),
                                      actionType = actionType,
                                      isSuccess = entity != null))
    }
    catch {
      case e : Exception => AppLogger.error(e, s"Failed at performing $actionType")
    }

    getEmptyResponse(actionType)
  }

  /**
   * Runs db.run(..) and returns the non async result.
   *
   * @param dbAction
   * @tparam T
   *
   * @return
   */
  def run[T >: Null <: AnyRef](dbAction : T) : Seq[TRow] = {
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
  def runAsync[T >: Null <: AnyRef](dbAction : T) : Future[Seq[TRow]] = {
    try {
      val results = getRunResult(dbAction).get
      AppLogger.logEntities(isLogQueries, results)
      return results
    }
    catch {
      case e : Exception => AppLogger.error(e)
    }

    null
  }

  def getRunResult[T >: Null <: AnyRef](dbAction : T) : Option[Future[Seq[TRow]]] = {
    dbAction match {
      case fixedSql : FixedSqlAction[Seq[TRow], _, _]
      =>
        Some(db.run(fixedSql))
      case fixedSqlStreaming : FixedSqlStreamingAction[Seq[TRow], NoStream, Effect.All] =>
        Some(db.run(fixedSqlStreaming))
      case sqlStreaming : SqlStreamingAction[Seq[TRow], NoStream, Effect.All] =>
        Some(db.run(sqlStreaming))
      case dbAction2 : DBIOAction[_, _, _] =>
        val x = db.call("run", dbAction2).asInstanceOf[Future[Seq[TRow]]];
        Some(x)
      case _ =>
        throw new InvalidDnDOperationException(s"Invalid operation for runAsync. Operation $dbAction")
    }
  }
}
