package services.core.traits

import shared.com.ortb.model.results.RepositoryOperationResult

trait BasicPersistentServiceOperations[TTable, TRow, TKey]
    extends BasicPersistentServiceCore[TTable, TRow, TKey] {
  def addUsingOption(
      entity: Option[TRow]): Option[RepositoryOperationResult[TRow, TKey]]

  def add(entity: TRow): RepositoryOperationResult[TRow, TKey]

  def getAll: List[TRow] = repository.getAll

  def update(id: TKey, entity: TRow): RepositoryOperationResult[TRow, TKey]

  def addOrUpdate(id: TKey, entity: TRow): RepositoryOperationResult[TRow, TKey]

  def delete(id: TKey): RepositoryOperationResult[TRow, TKey]

  def getById(id: TKey): Option[TRow]
}
