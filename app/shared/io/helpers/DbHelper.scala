package shared.io.helpers

import java.awt.dnd.InvalidDnDOperationException

import shared.com.ortb.enumeration.DatabaseActionType.DatabaseActionType
import shared.com.ortb.manager.traits.DefaultExecutionContextManagerConcreteImplementation
import shared.com.ortb.model.attributes.GenericResponseAttributesModel
import shared.com.ortb.model.results.RepositoryOperationResultModel
import shared.com.ortb.model.wrappers.persistent.EntityWrapper
import shared.io.helpers.traits.IterableHelperBase
import shared.io.loggers.AppLogger
import slick.dbio.{ DBIOAction, Effect, NoStream }
import slick.jdbc.SQLiteProfile
import slick.sql.{ FixedSqlAction, FixedSqlStreamingAction, SqlStreamingAction }

import scala.concurrent.{ Await, Future }
class EntityResponseHelper{
  lazy val defaultExecutionContextManager = new DefaultExecutionContextManagerConcreteImplementation

  protected def createResponseForAffectedRowCount[TRow, TKey](
    affectedRow : Int,
    entity : Option[TRow],
    actionType : DatabaseActionType,
    message : String = "",
    isSuccess : Boolean = true
  ) : RepositoryOperationResultModel[TRow, TKey] = {
    val message2 = getMessageForEntity(Some(affectedRow), actionType, message)
    val hasAffected = affectedRow > 0

    if (hasAffected && entity.isDefined) {
      AppLogger.logEntitiesNonFuture(isLogDatabaseQueryLogs, Seq(entity.get), message2)
    }

    if (hasAffected) {
      return createResponseFor(
        entityId = Some(getEntityIdFromOptionRow(entity)),
        entity = entity,
        actionType = actionType,
        message = message2,
        isSuccess = isSuccess
      )
    }

    throw new Exception(
      s"${ headerMessage } No record affected for operation: $actionType, Entity: $entity"
    )
  }

  protected def createResponseForAffectedRow(
    affectedEntity : Option[TRow],
    affectedRowsCount : Option[Int],
    actionType : DatabaseActionType,
    message : String = "",
    isSuccess : Boolean = true
  ) : RepositoryOperationResultModel[TRow, TKey] = {
    val message2 : String =
      getMessageForEntity(
        affectedRowsCount,
        actionType,
        message)

    if (affectedEntity != null) {
      AppLogger.logEntityNonFuture(
        isLogDatabaseQueryLogs,
        affectedEntity,
        message2
      )

      return createResponseFor(
        entityId = Some(getEntityIdFromOptionRow(affectedEntity)),
        entity = affectedEntity,
        actionType = actionType,
        message = message2,
        isSuccess = isSuccess
      )
    }

    getEmptyResponseFor(actionType)
  }

  def getEmptyResponseFor(actionType : DatabaseActionType)
  : RepositoryOperationResultModel[TRow, TKey] =
    AdapterHelper.repositoryAdapter.getEmptyResponse[TRow, TKey](
      actionType)

  protected def createResponseFor(
    entityId : Option[TKey],
    entity : Option[TRow],
    actionType : DatabaseActionType,
    message : String = "",
    isSuccess : Boolean = true
  ) : RepositoryOperationResultModel[TRow, TKey] = {
    val attributesModel =
      GenericResponseAttributesModel(
        isSuccess,
        id = Some(entity.get.toString),
        ids = None,
        Some(actionType),
        message)

    RepositoryOperationResultModel(
      Some(attributesModel),
      Some(EntityWrapper(entityId.get, entity.get))
    )
  }

  protected def getMessageForEntity(
    isLogDatabaseQueryLogs: Boolean,
    headerMessage : String,
    affectedRowsCount : Option[Int],
    actionType : DatabaseActionType,
    message : String) : String = {
    var message2 = message;
    if (message2.isEmpty) {
      var affectedCount = ""
      if (affectedRowsCount.isDefined) {
        affectedCount = s" (affected rows = ${ affectedRowsCount.get })"
      }

      message2 =
        s"${ headerMessage } [$actionType] operation is successful$affectedCount."
    }

    message2
  }

  def getEmptyResponseForInFuture[TRow, TKey](
    isLogDatabaseQueryLogs: Boolean,
    headerMessage : String,
    actionType : DatabaseActionType
  ) : Future[RepositoryOperationResultModel[TRow, TKey]] = {
    AppLogger.conditionalInfo(
      isLogDatabaseQueryLogs,
      s"${ headerMessage } $actionType is skipped."
    )

    Future {
      getEmptyResponseFor(actionType)
    }
  }
}
class DbHelperBase {
}

object DbHelper extends DbHelperBase {

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

  def saveAsync[TRow,TKey](
    db : SQLiteProfile.backend.DatabaseDef,
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
        AppLogger.error(e, s"Failed at performing $actionType")
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
  def runAs[T >: Null <: AnyRef, TRow2](
    db : SQLiteProfile.backend.DatabaseDef,
    dbAction: T): Seq[TRow2] = {
    Await.result(this.runAsyncAs[T, TRow2](db, dbAction), defaultTimeout)
  }

  /**
   * Runs db.run(..) and returns the non async result.
   *
   * @param dbAction
   * @tparam T
   *
   * @return
   */
  def run[T >: Null <: AnyRef](
    db : SQLiteProfile.backend.DatabaseDef,
    dbAction: T): Seq[TRow] = {
    Await.result(this.runAsync(dbAction), defaultTimeout)
  }


  def getRunResultAs[T >: Null <: AnyRef, TRow2](
    db : SQLiteProfile.backend.DatabaseDef,
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
      AppLogger.logEntities(isLogDatabaseQueryLogs, results)
      return results
    } catch {
      case e: Exception => AppLogger.error(e)
    }

    null
  }
}
