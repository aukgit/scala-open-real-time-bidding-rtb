package shared.com.repository.traits.implementions.operations.mutations.async

import shared.com.ortb.enumeration.DatabaseActionType
import shared.com.ortb.model.repository.response.RepositoryOperationResultModel
import shared.com.repository.RepositoryBase
import shared.com.repository.traits.operations.mutations.async.RepositoryDeleteOperationsAsync
import shared.io.loggers.AppLogger

import scala.concurrent.Future

trait RepositoryDeleteOperationsAsyncImplementation[TTable, TRow, TKey]
  extends RepositoryDeleteOperationsAsync[TTable, TRow, TKey] {
  this : RepositoryBase[TTable, TRow, TKey] =>
  def deleteAsync(
    entityId : TKey
  ) : Future[RepositoryOperationResultModel[TRow, TKey]] = {
    val entity = getById(entityId)
    val actionType = DatabaseActionType.Delete

    try {
      val deleteAction = getDeleteAction(entityId)

      return this.saveAsync(
        entity = entity,
        dbAction = deleteAction,
        actionType
      )
    } catch {
      case e : Exception =>
        AppLogger.error(
          e,
          s"${headerMessage} Delete failed on [id:$entityId, entity: $entity]"
        )
    }

    getEmptyResponseForInFuture(actionType)
  }
}
