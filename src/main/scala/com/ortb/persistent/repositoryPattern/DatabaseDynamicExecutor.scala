package com.ortb.persistent.repositoryPattern

import java.awt.dnd.InvalidDnDOperationException

import com.ortb.enumeration.DatabaseActionType.DatabaseActionType
import com.ortb.manager.AppManager
import com.ortb.model.results.RepositoryOperationResult
import com.ortb.persistent.schema.DatabaseSchema
import io.{AppLogger, ReflectionHelper}
import slick.dbio.{Effect, NoStream}
import slick.sql._

import scala.concurrent.Future

class DatabaseDynamicExecutor[TTable, TRow, TKey](appManager: AppManager)
    extends DatabaseSchema(appManager: AppManager) {

  def quickSave(
    dbAction: FixedSqlAction[Int, NoStream, Effect.Write],
    actionType: DatabaseActionType,
    headerMessage: String
  ): RepositoryOperationResult[TRow, TKey] = {
    try {
      val result = db
        .run(dbAction)
        .map(
          (affectedRowsCount: Int) =>
            createResponseForAffectedRowCount(
              affectedRow = affectedRowsCount,
              entity = None,
              actionType = actionType
          )
        )

      return toRegular(result, defaultTimeout)
    } catch {
      case e: Exception =>
        AppLogger.error(e, s"${headerMessage} Failed at performing $actionType")
    }

    getEmptyResponseFor(actionType)
  }

  def saveAsync(
    entityKey: TKey,
    entity: TRow,
    dbAction: FixedSqlAction[Int, NoStream, Effect.Write],
    isPerformActionOnExist: Boolean,
    actionType: DatabaseActionType
  ): Future[RepositoryOperationResult[TRow, TKey]] = {
    val isPerform = this.isExists(entityKey) == isPerformActionOnExist
    if (isPerform) {
      return saveAsync(Some(entity), dbAction = dbAction, actionType)
    }

    getEmptyResponseForInFture(actionType)
  }

  def saveAsync(
    entity: Option[TRow],
    dbAction: FixedSqlAction[Int, NoStream, Effect.Write],
    actionType: DatabaseActionType
  ): Future[RepositoryOperationResult[TRow, TKey]] = {
    try {
      return db
        .run(dbAction)
        .map(
          (affectedRowsCount: Int) =>
            createResponseForAffectedRowCount(
              affectedRowsCount,
              entity,
              actionType
          )
        )
    } catch {
      case e: Exception =>
        AppLogger.error(e, s"$headerMessage Failed at performing $actionType")
    }

    getEmptyResponseForInFture(actionType)
  }

  def saveAsync(
    dbAction: FixedSqlAction[TRow, NoStream, Effect.Write],
    actionType: DatabaseActionType
  ): Future[RepositoryOperationResult[TRow, TKey]] = {
    try {
      return db
        .run(dbAction)
        .map(
          (entity: TRow) =>
            this.createResponseForAffectedRow(
              affectedEntity = Some(entity),
              actionType = actionType,
              isSuccess = entity != null,
              affectedRowsCount = Some(1)
          )
        )
    } catch {
      case e: Exception =>
        AppLogger.error(e, s"${headerMessage} Failed at performing $actionType")
    }

    getEmptyResponseForInFture(actionType)
  }

  /**
    * runs db.run(..)
    *
    * @param dbAction
    * @tparam T
    *
    * @return
    */
  def runAsync[T >: Null <: AnyRef](
    dbAction: T,
    databaseActionType: DatabaseActionType
  ): Future[Seq[TRow]] = {
    try {
      val results = this.getRunResult(dbAction, databaseActionType).get
      AppLogger.logEntities(isLogQueries, results)
      return results
    } catch {
      case e: Exception => AppLogger.error(e)
    }

    null
  }

  def getRunResult[T >: Null <: AnyRef](
    dbAction: T,
    databaseActionType: DatabaseActionType
  ): Option[Future[Seq[TRow]]] = {
    dbAction match {
      case fixedSql: FixedSqlAction[Seq[TRow], _, _] =>
        Some(db.run(fixedSql))
      case fixedSqlStreaming: FixedSqlStreamingAction[Seq[TRow], NoStream, Effect.All] =>
        Some(db.run(fixedSqlStreaming))
      case sqlStreaming: SqlStreamingAction[Seq[TRow], NoStream, Effect.All] =>
        Some(db.run(sqlStreaming))
      case _ =>
        throw new InvalidDnDOperationException(
          s"Invalid operation for runAsync. Operation $databaseActionType and given action ${ReflectionHelper
            .getTypeName(dbAction)}"
        )
    }
  }
}
