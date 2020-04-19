package shared.com.repository.traits.operations.mutations

import shared.com.ortb.model.results.RepositoryOperationResult
import shared.com.ortb.model.wrappers.persistent.EntityWrapper

trait RepositoryAddOrUpdateOperations[TTable, TRow, TKey]
  extends RepositoryOperationsBase[TRow] {
  def addOrUpdate(
    entityId : TKey,
    entity   : TRow) : RepositoryOperationResult[TRow, TKey]

  def addOrUpdateEntities(
    entityWrappers : Iterable[EntityWrapper[TRow, TKey]]
  ) : Iterable[RepositoryOperationResult[TRow, TKey]]

}
