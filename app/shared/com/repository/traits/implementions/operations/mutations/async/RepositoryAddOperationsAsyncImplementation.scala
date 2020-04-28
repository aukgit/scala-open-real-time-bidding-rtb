package shared.com.repository.traits.implementions.operations.mutations.async

import shared.com.ortb.enumeration.DatabaseActionType
import shared.com.ortb.model.results.RepositoryOperationResultModel
import shared.com.repository.RepositoryBase
import shared.com.repository.traits.operations.mutations.async.RepositoryAddOperationsAsync
import shared.io.loggers.AppLogger

import scala.concurrent.Future

trait RepositoryAddOperationsAsyncImplementation[TTable, TRow, TKey]
  extends RepositoryAddOperationsAsync[TTable, TRow, TKey] {
  this : RepositoryBase[TTable, TRow, TKey] =>

  def addAsync(entity : TRow) : Future[RepositoryOperationResultModel[TRow, TKey]] = {
    val actionType = DatabaseActionType.Create

    try {
      val action = getAddAction(entity)
      val result = this.saveAsync(
        dbAction = action,
        DatabaseActionType.Create)

      traceFutureResult(
        isLogDatabaseActionsToDatabase,
        "addAsync",
        Some(result),
        actionType)

      return result
    } catch {
      case e : Exception =>
        AppLogger.error(e, s"${headerMessage} Add failed on [entity: $entity]")
    }

    getEmptyResponseForInFuture(actionType)
  }
}
