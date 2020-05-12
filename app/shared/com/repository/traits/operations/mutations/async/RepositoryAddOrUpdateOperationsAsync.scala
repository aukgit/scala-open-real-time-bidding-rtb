package shared.com.repository.traits.operations.mutations.async

import shared.com.ortb.model.results.RepositoryOperationResultModel
import shared.com.repository.traits.operations.mutations.RepositoryOperationsBase

import scala.concurrent.Future

trait RepositoryAddOrUpdateOperationsAsync[TTable, TRow, TKey]
  extends RepositoryOperationsBase[TRow] {
  def addOrUpdateAsync(
    entityId : TKey,
    entity : TRow
  ) : Future[RepositoryOperationResultModel[TRow, TKey]]
}
