package shared.com.repository.traits.operations.mutations

import shared.com.ortb.model.results.RepositoryOperationResult

trait RepositoryAddOperations[TTable, TRow, TKey]
  extends RepositoryOperationsBase[TRow] {
  def add(entity : TRow) : RepositoryOperationResult[TRow, TKey]

  def addEntities(
    entities : Iterable[TRow]
  ) : Iterable[RepositoryOperationResult[TRow, TKey]]


  def addEntities(
    entity : TRow,
    addTimes : Int
  ) : Iterable[RepositoryOperationResult[TRow, TKey]]
}
