package shared.com.repository.traits.operations.mutations

import shared.com.ortb.model.results.{ RepositoryOperationResultModel, RepositoryOperationResultsModel }
import shared.com.ortb.model.wrappers.persistent.EntityWrapperModel

trait RepositoryAddOrUpdateOperations[TTable, TRow, TKey]
  extends RepositoryOperationsBase[TRow] {
  def addOrUpdate(
    entityId : TKey,
    entity   : TRow) : RepositoryOperationResultModel[TRow, TKey]

  def addOrUpdateEntities(
    entityWrappers : Iterable[EntityWrapperModel[TRow, TKey]]
  ) : RepositoryOperationResultsModel[TRow, TKey]
}
