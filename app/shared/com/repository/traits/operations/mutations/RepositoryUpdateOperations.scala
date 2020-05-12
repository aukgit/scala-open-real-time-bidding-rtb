package shared.com.repository.traits.operations.mutations

import shared.com.ortb.model.results.{ RepositoryOperationResultModel, RepositoryOperationResultsModel }
import shared.com.ortb.model.wrappers.persistent.EntityWrapperModel

trait RepositoryUpdateOperations[TTable, TRow, TKey]
  extends RepositoryOperationsBase[TRow] {
  def update(
    entityId : TKey,
    entity   : TRow) : RepositoryOperationResultModel[TRow, TKey]

  def updateEntities(
    entityWrappers : Iterable[EntityWrapperModel[TRow, TKey]]
  ) : RepositoryOperationResultsModel[TRow, TKey]
}
