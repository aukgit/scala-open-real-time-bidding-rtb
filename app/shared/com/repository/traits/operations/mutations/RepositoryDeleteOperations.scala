package shared.com.repository.traits.operations.mutations

import shared.com.ortb.model.results.RepositoryOperationResult

trait RepositoryDeleteOperations[TTable, TRow, TKey]
  extends RepositoryOperationsBase[TRow] {
  def delete(entityId : TKey) : RepositoryOperationResult[TRow, TKey]


  def deleteEntities(
    entities : Iterable[TKey]
  ) : Iterable[RepositoryOperationResult[TRow, TKey]]
}
