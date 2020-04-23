package shared.com.repository.traits.operations.mutations

import shared.com.ortb.model.repository.response.RepositoryOperationResultsModel
import shared.com.ortb.model.results.{ RepositoryOperationResultModel, RepositoryOperationResultsModel }

trait RepositoryAddOperations[TTable, TRow, TKey]
  extends RepositoryOperationsBase[TRow] {
  def add(entity : TRow) : RepositoryOperationResultModel[TRow, TKey]

  def addEntities(
    entity : TRow,
    addTimes : Int
  ) : RepositoryOperationResultsModel[TRow, TKey]

  def addEntities(
    entities : Iterable[TRow]
  ) : RepositoryOperationResultsModel[TRow, TKey]
}
