package com.ortb.persistent.repositoryPattern.traits

import com.ortb.enumeration.DatabaseActionType.DatabaseActionType
import com.ortb.model.results.RepositoryOperationResult
import com.ortb.persistent.repositoryPattern.Repository
import io.AppLogger

import scala.concurrent.Future

trait EntityResponseCreator[TTable, TRow, TKey] {
  this : Repository[TTable, TRow, TKey] =>

  lazy protected val emptyResponse = new RepositoryOperationResult[TRow, TKey](
    false,
    entityId = None,
    entity = None)

  protected def createResponseFor(
    entityId : Option[TKey],
    entity : TRow,
    message : String = "",
    isSuccess : Boolean = true
  ) : RepositoryOperationResult[TRow, TKey] = {
    RepositoryOperationResult(isSuccess, entityId, Some(entity), message)
  }

  protected def createResponseForAffectedRowCount(
    affectedRow : Int,
    entity : TRow,
    actionType : DatabaseActionType,
    message : String = "",
    isSuccess : Boolean = true,
  ) : RepositoryOperationResult[TRow, TKey] = {
    val message2 = getMessageForEntity(entity, actionType, message)

    if (affectedRow > 0) {
      AppLogger.logEntitiesNonFuture(isLogQueries, Seq(entity), message2)
      return createResponseFor(Some(getIdOf(entity)), entity, message2, isSuccess)
    }

    emptyResponse;
  }

  private def getMessageForEntity(
    entity : TRow,
    actionType : DatabaseActionType,
    message : String) = {
    var message2 = message;
    if (message2.isEmpty) {
      message2 = s"Operation [$actionType] successful for Entity($entity)."
    }

    message2
  }

  protected def createResponseForAffectedRow(
    affectedEntity : TRow,
    actionType : DatabaseActionType,
    message : String = "",
    isSuccess : Boolean = true
  ) : RepositoryOperationResult[TRow, TKey] = {
    val message2 : String = getMessageForEntity(affectedEntity, actionType, message)

    if (affectedEntity != null) {
      AppLogger.logEntitiesNonFuture(isLogQueries, Seq(affectedEntity), message2)
      return createResponseFor(
        entityId = Some(getIdOf(affectedEntity)),
        entity = affectedEntity,
        message = message2,
        isSuccess = isSuccess)
    }

    emptyResponse;
  }

  protected def getEmptyResponse : Future[RepositoryOperationResult[TRow, TKey]] = {
    AppLogger.conditionalInfo(isLogQueries, s"Operation Skipped.")

    Future {
      emptyResponse
    }
  }
}
