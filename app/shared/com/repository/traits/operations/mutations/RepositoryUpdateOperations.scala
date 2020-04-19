package shared.com.repository.traits.operations.mutations

import shared.com.ortb.model.results.RepositoryOperationResult
import shared.com.ortb.model.wrappers.persistent.EntityWrapper

trait RepositoryUpdateOperations[TTable, TRow, TKey]
  extends RepositoryOperationsBase[TRow] {
  def update(
    entityId     : TKey,
    entity       : TRow) : RepositoryOperationResult[TRow, TKey]

  def updateEntities(
    entityWrappers : Iterable[EntityWrapper[TRow, TKey]]
  ) : Iterable[RepositoryOperationResult[TRow, TKey]]
}
