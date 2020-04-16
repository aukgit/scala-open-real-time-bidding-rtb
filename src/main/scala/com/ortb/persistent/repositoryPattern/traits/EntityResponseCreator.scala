package com.ortb.persistent.repositoryPattern.traits

import com.ortb.enumeration.DatabaseActionType.DatabaseActionType
import com.ortb.model.results.RepositoryOperationResult
import io.AppLogger

import scala.concurrent.{ExecutionContext, Future}

trait EntityResponseCreator[TTable, TRow, TKey] {
  implicit protected lazy val executionContext : Future[RepositoryOperationResult[TRow, TKey]] =  {
    ExecutionContext.fromExecutor(
      new java.util.concurrent.ForkJoinPool(3)
    ).prepare()

   def createResponseForAffectedRowCount(
    isLogQueries: Boolean,
    entityId: Option[TKey],
    affectedRow: Int,
    entity: Option[TRow],
    actionType: DatabaseActionType,
    message: String = "",
    isSuccess: Boolean = true,
    headerMessage: String
  ): RepositoryOperationResult[TRow, TKey] = {
    val message2 = getMessageForEntity(Some(affectedRow), actionType, message)
    val hasAffected = affectedRow > 0

    if (hasAffected && entity.isDefined) {
      AppLogger.logEntitiesNonFuture(isLogQueries, Seq(entity.get), message2)
    }

    if (hasAffected) {
      return createResponseFor(
        entityId = entityId,
        entity = entity,
        actionType = actionType,
        message = message2,
        isSuccess = isSuccess
      )
    }

    throw new Exception(
      s"${headerMessage} No record affected for operation: $actionType, Entity: $entity"
    )
  }

  protected def createResponseForAffectedRow(
    isLogQueries: Boolean,
    entityId: Option[TKey],
    affectedEntity: Option[TRow],
    affectedRowsCount: Option[Int],
    actionType: DatabaseActionType,
    message: String = "",
    isSuccess: Boolean = true
  ): RepositoryOperationResult[TRow, TKey] = {
    val message2: String =
      getMessageForEntity(affectedRowsCount, actionType, message)

    if (affectedEntity != null) {
      AppLogger.logEntitiesNonFuture(
        isLogQueries,
        Seq(affectedEntity),
        message2
      )
      return createResponseFor(
        entityId = entityId,
        entity = affectedEntity,
        actionType = actionType,
        message = message2,
        isSuccess = isSuccess
      )
    }

    getEmptyResponseFor(actionType)
  }

  def getEmptyResponseFor(actionType: DatabaseActionType) =
    new RepositoryOperationResult[TRow, TKey](
      isSuccess = false,
      entityId = None,
      entity = None,
      actionType = actionType
    )

  protected def createResponseFor(
    entityId: Option[TKey],
    entity: Option[TRow],
    actionType: DatabaseActionType,
    message: String = "",
    isSuccess: Boolean = true
  ): RepositoryOperationResult[TRow, TKey] = {
    RepositoryOperationResult(isSuccess, entityId, entity, actionType, message)
  }

  private def getMessageForEntity(affectedRowsCount: Option[Int],
                                  actionType: DatabaseActionType,
                                  message: String,
                                  headerMessage: String) = {
    var message2 = message;
    if (message2.isEmpty) {
      var affectedCount = ""
      if (affectedRowsCount.isDefined) {
        affectedCount = s" (affected rows = ${affectedRowsCount.get})"
      }

      message2 =
        s"${headerMessage} [$actionType] operation is successful$affectedCount."
    }

    message2
  }

  protected def getEmptyResponseForInFuture(
    isLogQueries: Boolean,
    actionType: DatabaseActionType,
    headerMessage: String
  ): Future[RepositoryOperationResult[TRow, TKey]] = {
    AppLogger.conditionalInfo(
      isLogQueries,
      s"${headerMessage} $actionType is skipped."
    )

    Future {
      getEmptyResponseFor(actionType)
    }
  }
}
