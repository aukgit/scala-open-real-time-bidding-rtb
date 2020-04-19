package shared.com.repository.traits.operations.mutations

import shared.com.ortb.model.repository.response.{RepositoryOperationResultModel, RepositoryOperationResultsModel}

trait RepositoryDeleteOperations[TTable, TRow, TKey]
  extends RepositoryOperationsBase[TRow] {
  def delete(entityId : TKey) : RepositoryOperationResultModel[TRow, TKey]

  def deleteEntities(
    entities : Iterable[TKey]
  ) : RepositoryOperationResultsModel[TRow, TKey]
}
