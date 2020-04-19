package shared.com.repository.traits.operations.mutations

import shared.com.ortb.model.repository.response.{RepositoryOperationResultModel, RepositoryOperationResultsModel}
import shared.com.ortb.model.wrappers.persistent.EntityWrapper

trait RepositoryAddOrUpdateOperations[TTable, TRow, TKey]
  extends RepositoryOperationsBase[TRow] {
  def addOrUpdate(
    entityId : TKey,
    entity   : TRow) : RepositoryOperationResultModel[TRow, TKey]

  def addOrUpdateEntities(
    entityWrappers : Iterable[EntityWrapper[TRow, TKey]]
  ) : RepositoryOperationResultsModel[TRow, TKey]
}
