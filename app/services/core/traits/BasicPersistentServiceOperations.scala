package services.core.traits

import shared.com.ortb.model.results.RepositoryOperationResult
import shared.com.ortb.persistent.repositories.pattern.RepositoryBase
import shared.io.helpers.EmptyValidateHelper

trait BasicPersistentServiceOperations[TTable, TRow, TKey] {
  val repository : RepositoryBase[TTable, TRow, TKey]

  def addUsingOption(entity : Option[TRow]) : Option[RepositoryOperationResult[TRow, TKey]] ={
    if(EmptyValidateHelper.isEmpty(entity)){
      return None
    }

    Some(add(entity.get))
  }

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
