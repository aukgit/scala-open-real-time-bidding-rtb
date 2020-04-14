package com.ortb.persistent.repositoryPattern.traits

import com.ortb.enumeration.DatabaseActionType.DatabaseActionType
import com.ortb.model.results.RepositoryOperationResult
import com.ortb.persistent.repositoryPattern.Repository
import io.AppLogger

import scala.concurrent.Future

trait EntityResponseCreator[TTable, TRow, TKey] {
  this : Repository[TTable, TRow, TKey] =>

  def getEmptyResponseFor(actionType : DatabaseActionType) = new RepositoryOperationResult[TRow, TKey](
    isSuccess = false,
    entityId = None,
    entity = None,
    actionType = actionType)

  protected def createResponseForAffectedRowCount(
    affectedRow : Int,
    entity      : Option[TRow],
    actionType  : DatabaseActionType,
    message     : String = "",
    isSuccess   : Boolean = true,
  ) : RepositoryOperationResult[TRow, TKey] = {
    val message2 = getMessageForEntity(entity, actionType, message)

    if (affectedRow > 0) {
      AppLogger.logEntitiesNonFuture(isLogQueries && entity.isDefined, Seq(entity.get), message2)

      return createResponseFor(
        entityId = Some(getIdOf(entity)),
        entity = entity,
        actionType = actionType,
        message = message2,
        isSuccess = isSuccess)
    }

    throw new Exception(s"No record affected for operation: $actionType, Entity: $entity")
  }

  protected def createResponseFor(
    entityId : Option[TKey],
    entity : Option[TRow],
    actionType : DatabaseActionType,
    message    : String = "",
    isSuccess  : Boolean = true
  ) : RepositoryOperationResult[TRow, TKey] = {
    RepositoryOperationResult(isSuccess, entityId, entity, actionType, message)
  }

  private def getMessageForEntity(
    entity     : Option[TRow],
    actionType : DatabaseActionType,
    message    : String) = {
    var message2 = message;
    if (message2.isEmpty) {
      message2 = s"Operation [$actionType] successful."
    }

    message2
  }

  protected def createResponseForAffectedRow(
    affectedEntity : Option[TRow],
    actionType     : DatabaseActionType,
    message        : String = "",
    isSuccess      : Boolean = true
  ) : RepositoryOperationResult[TRow, TKey] = {
    val message2 : String = getMessageForEntity(affectedEntity, actionType, message)

    if (affectedEntity != null) {
      AppLogger.logEntitiesNonFuture(isLogQueries, Seq(affectedEntity), message2)
      return createResponseFor(
        entityId = Some(getIdOf(affectedEntity)),
        entity = affectedEntity,
        actionType = actionType,
        message = message2,
        isSuccess = isSuccess)
    }

    getEmptyResponseFor(actionType)
  }

  protected def getEmptyResponse(actionType : DatabaseActionType) : Future[RepositoryOperationResult[TRow, TKey]] = {
    AppLogger.conditionalInfo(isLogQueries, s"$actionType is skipped.")

    Future {
      getEmptyResponseFor(actionType)
    }
  }
}
