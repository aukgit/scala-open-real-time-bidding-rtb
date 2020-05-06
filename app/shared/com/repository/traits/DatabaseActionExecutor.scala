package shared.com.repository.traits

import java.awt.dnd.InvalidDnDOperationException

import shared.com.ortb.enumeration.DatabaseActionType.DatabaseActionType
import shared.com.ortb.implicits.implementations.ImplicitsImplementation.anyRefCaller
import shared.com.ortb.model.results.RepositoryOperationResultModel
import shared.com.repository.RepositoryBase
import shared.io.loggers.AppLogger
import slick.dbio._
import slick.sql._

import scala.concurrent.{ Await, Future }

trait DatabaseActionExecutor[TTable, TRow, TKey] {
  this: RepositoryBase[TTable, TRow, TKey] =>

  def quickSave(
    dbAction: FixedSqlAction[Int, NoStream, Effect.Write],
    actionType: DatabaseActionType
  ): RepositoryOperationResultModel[TRow, TKey] = {
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
    entity: Option[TRow],
    dbAction: FixedSqlAction[Int, NoStream, Effect.Write],
    actionType: DatabaseActionType
  ): Future[RepositoryOperationResultModel[TRow, TKey]] = {
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

    getEmptyResponseForInFuture(actionType)
  }

  def saveAsync(
    dbAction: FixedSqlAction[TRow, NoStream, Effect.Write],
    actionType: DatabaseActionType
  ): Future[RepositoryOperationResultModel[TRow, TKey]] = {
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

    getEmptyResponseForInFuture(actionType)
  }

  /**
   * Runs for query db.run(Query) and returns the non async result.
   *
   * @param dbAction
   * @tparam T
   *
   * @return
   */
  def runAs[T >: Null <: AnyRef, TRow2](dbAction: T): Seq[TRow2] = {
    Await.result(this.runAsyncAs[T, TRow2](dbAction), defaultTimeout)
  }

  /**
    * Runs db.run(..) and returns the non async result.
    *
    * @param dbAction
    * @tparam T
    *
    * @return
    */
  def run[T >: Null <: AnyRef](dbAction: T): Seq[TRow] = {
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
  def runAsyncAs[T >: Null <: AnyRef, TRow2](dbAction: T): Future[Seq[TRow2]] = {
    try {
      val results = getDatabaseQueryResultAs[T, TRow2](dbAction).get
      AppLogger.logEventualEntitiesWithCondition(isLogDatabaseQueryLogs, results)
      return results
    } catch {
      case e: Exception => AppLogger.error(e)
    }

    null
  }


  /**
    * runs db.run(..)
    *
    * @param dbAction
    * @tparam T
    *
    * @return
    */
  def runAsync[T >: Null <: AnyRef](dbAction: T): Future[Seq[TRow]] = {
    try {
      val results = getDatabaseQueryResult(dbAction).get
      AppLogger.logEventualEntitiesWithCondition(isLogDatabaseQueryLogs, results)
      return results
    } catch {
      case e: Exception => AppLogger.error(e)
    }

    null
  }

  def getRunResultAs[T >: Null <: AnyRef, TRow2](
    dbAction: T
  ): Option[Future[Seq[TRow2]]] = {
    dbAction match {
      case fixedSql: FixedSqlAction[_, _, _] =>
        Some(db.run(fixedSql).asInstanceOf[Future[Seq[TRow2]]])
      case fixedSqlStreaming: FixedSqlStreamingAction[_, _, _] =>
        Some(db.run(fixedSqlStreaming).asInstanceOf[Future[Seq[TRow2]]])
      case sqlStreaming: SqlStreamingAction[_, _, _] =>
        Some(db.run(sqlStreaming).asInstanceOf[Future[Seq[TRow2]]])
      case dbAction2: DBIOAction[_, _, _] =>
        val dbIOAction = db.call("run", dbAction2).asInstanceOf[Future[Seq[TRow2]]];
        Some(dbIOAction)
      case _ =>
        throw new InvalidDnDOperationException(
          s"${headerMessage} Invalid operation for runAsync. Operation $dbAction"
        )
    }
  }

  /**
   * Used for running Query -> Seq results in future
   * @param dbAction
   * @tparam T
   * @return
   */
  def getDatabaseQueryResultAs[T >: Null <: AnyRef, TRow2](
    dbAction: T
  ): Option[Future[Seq[TRow2]]] = {
    getRunResultAs[T, TRow2](dbAction)
  }

  /**
   * Used for running Query -> Seq results in future
   * @param dbAction
   * @tparam T
   * @return
   */
  def getDatabaseQueryResult[T >: Null <: AnyRef](
    dbAction: T
  ): Option[Future[Seq[TRow]]] = {
    getRunResultAs[T, TRow](dbAction)
  }
}
