package shared.com.repository.traits.implementions.operations

import shared.com.ortb.model.results.RepositoryOperationResult
import shared.com.repository.RepositoryBase
import shared.com.repository.traits.operations.mutations.RepositoryDeleteOperations

trait RepositoryDeleteOperationsImplementation[TTable, TRow, TKey]
  extends RepositoryDeleteOperations[TTable, TRow, TKey] {
  this : RepositoryBase[TTable, TRow, TKey] =>
  def delete(entityId     : TKey) : RepositoryOperationResult[TRow, TKey] =
    toRegular(deleteAsync(entityId), defaultTimeout)

  def deleteEntities(
    entities      : Iterable[TKey]
  ) : Iterable[RepositoryOperationResult[TRow, TKey]] = {
    val responses = deleteEntitiesAsync(entities)

    if (responses == null || responses.isEmpty) {
      return null
    }

    responses.map(futureResponse => toRegular(futureResponse, defaultTimeout))
  }
}
