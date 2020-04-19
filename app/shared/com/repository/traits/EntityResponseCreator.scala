package shared.com.repository.traits

import shared.com.ortb.enumeration.DatabaseActionType.DatabaseActionType
import shared.com.ortb.model.repository.response.RepositoryOperationResultModel
import shared.com.repository.RepositoryBase
import shared.io.loggers.AppLogger

import scala.concurrent.Future

trait EntityResponseCreator[TTable, TRow, TKey] {
  this: RepositoryBase[TTable, TRow, TKey] =>
  protected def createResponseForAffectedRowCount(
    affectedRow: Int,
    entity: Option[TRow],
    actionType: DatabaseActionType,
    message: String = "",
    isSuccess: Boolean = true,
  ): RepositoryOperationResultModel[TRow, TKey] = {
    val message2 = getMessageForEntity(Some(affectedRow), actionType, message)
    val hasAffected = affectedRow > 0

    if (hasAffected && entity.isDefined) {
      AppLogger.logEntitiesNonFuture(isLogQueries, Seq(entity.get), message2)
    }

    if (hasAffected) {
      return createResponseFor(
        entityId = Some(getEntityId(entity)),
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
    affectedEntity: Option[TRow],
    affectedRowsCount: Option[Int],
    actionType: DatabaseActionType,
    message: String = "",
    isSuccess: Boolean = true
  ): RepositoryOperationResultModel[TRow, TKey] = {
    val message2: String =
      getMessageForEntity(affectedRowsCount, actionType, message)

    if (affectedEntity != null) {
      AppLogger.logEntitiesNonFuture(
        isLogQueries,
        Seq(affectedEntity),
        message2
      )
      return createResponseFor(
        entityId = Some(getEntityId(affectedEntity)),
        entity = affectedEntity,
        actionType = actionType,
        message = message2,
        isSuccess = isSuccess
      )
    }

    getEmptyResponseFor(actionType)
  }

  def getEmptyResponseFor(actionType: DatabaseActionType) =
    new RepositoryOperationResultModel[TRow, TKey](
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
  ): RepositoryOperationResultModel[TRow, TKey] = {
    RepositoryOperationResult(isSuccess, entityId, entity, actionType, message)
  }

  private def getMessageForEntity(affectedRowsCount: Option[Int],
                                  actionType: DatabaseActionType,
                                  message: String) = {
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
    actionType: DatabaseActionType
  ): Future[RepositoryOperationResultModel[TRow, TKey]] = {
    AppLogger.conditionalInfo(
      isLogQueries,
      s"${headerMessage} $actionType is skipped."
    )

    Future {
      getEmptyResponseFor(actionType)
    }
  }
}
