package shared.com.repository.traits.operations.mutations

import shared.com.ortb.model.repository.response.RepositoryOperationResultsModel
import shared.com.ortb.model.results.{ RepositoryOperationResultModel, RepositoryOperationResultsModel }
import shared.com.ortb.model.wrappers.persistent.EntityWrapper

trait RepositoryUpdateOperations[TTable, TRow, TKey]
  extends RepositoryOperationsBase[TRow] {
  def update(
    entityId : TKey,
    entity       : TRow) : RepositoryOperationResultModel[TRow, TKey]

  def updateEntities(
    entityWrappers : Iterable[EntityWrapper[TRow, TKey]]
  ) : RepositoryOperationResultsModel[TRow, TKey]
}
