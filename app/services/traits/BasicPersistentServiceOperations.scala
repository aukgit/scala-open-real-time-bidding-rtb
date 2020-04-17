package services.traits

import shared.com.ortb.model.results.RepositoryOperationResult
import shared.com.ortb.persistent.repositories.pattern.RepositoryBase

trait BasicPersistentServiceOperations[TTable, TRow, TKey] {
  val repository : RepositoryBase[TTable, TRow, TKey]

  def add(entity : TRow) : RepositoryOperationResult[TRow, TKey] =
    repository.add(entity)

  def getAll : List[TRow] = repository.getAll

  def update(id : TKey, entity : TRow) : RepositoryOperationResult[TRow, TKey] =
    repository.update(id, entity)

  def addOrUpdate(
    id              : TKey,
    entity          : TRow) : RepositoryOperationResult[TRow, TKey] =
    repository.addOrUpdate(id, entity)

  def delete(id : TKey) : RepositoryOperationResult[TRow, TKey] =
    repository.delete(id)

  def getById(id : TKey) : Option[TRow] = repository.getById(id)
}
