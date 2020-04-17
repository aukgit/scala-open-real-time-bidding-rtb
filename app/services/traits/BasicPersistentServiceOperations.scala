package services.traits

import shared.com.ortb.model.results.RepositoryOperationResult
import shared.com.ortb.persistent.repositories.pattern.RepositoryBase

trait BasicPersistentServiceOperations[TTable, TRow, TKey] {
  val repository: RepositoryBase[TTable, TRow, TKey]

  def add(entity: TRow): RepositoryOperationResult[TRow, TKey]

  def getAll: List[TRow]

  def update(id: TKey, entity: TRow): RepositoryOperationResult[TRow, TKey]

  def addOrUpdate(id: TKey, entity: TRow): RepositoryOperationResult[TRow, TKey]

  def delete(id: TKey): RepositoryOperationResult[TRow, TKey]

  def getById(id: TKey): TRow
}
