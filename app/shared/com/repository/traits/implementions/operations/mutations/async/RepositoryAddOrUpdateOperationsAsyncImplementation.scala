package shared.com.repository.traits.implementions.operations.mutations.async

import shared.com.ortb.enumeration.DatabaseActionType
import shared.com.ortb.model.results.RepositoryOperationResultModel
import shared.com.repository.RepositoryBase
import shared.com.repository.traits.operations.mutations.async.RepositoryAddOrUpdateOperationsAsync

import scala.concurrent.Future

trait RepositoryAddOrUpdateOperationsAsyncImplementation[TTable, TRow, TKey]
  extends RepositoryAddOrUpdateOperationsAsync[TTable, TRow, TKey] {
  this : RepositoryBase[TTable, TRow, TKey] =>

  def addOrUpdateAsync(
    entityId : TKey,
    entity : TRow
  ) : Future[RepositoryOperationResultModel[TRow, TKey]] = {
    if (isExists(entityId)) {
      // update
      val result = updateAsync(entityId, entity)

      traceFutureResult(
        isLogQueries,
        "addOrUpdateAsync",
        Some(result),
        DatabaseActionType.AddOrUpdate)

      return result
    }

    // add
    addAsync(entity)
  }
}
