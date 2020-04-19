package services.core.traits.implementations

import services.core.traits.BasicPersistentServiceOperations
import shared.com.ortb.model.results.RepositoryOperationResult
import shared.io.helpers.EmptyValidateHelper

trait BasicPersistentServiceOperationsImplementation[TTable, TRow, TKey]
  extends BasicPersistentServiceOperations[TTable, TRow, TKey] {
  def addUsingOption(entity : Option[TRow]) : Option[RepositoryOperationResult[TRow, TKey]] = {
    if (EmptyValidateHelper.isEmpty(entity)) {
      return None
    }

    Some(add(entity.get))
  }

  def add(entity : TRow) : RepositoryOperationResult[TRow, TKey] =
    repository.add(entity)

  override def getAll : List[TRow] = repository.getAllAsList

  def update(id : TKey, entity : TRow) : RepositoryOperationResult[TRow, TKey] =
    repository.update(id, entity)

  def addOrUpdate(
    id : TKey,
    entity : TRow) : RepositoryOperationResult[TRow, TKey] =
    repository.addOrUpdate(id, entity)

  def delete(id : TKey) : RepositoryOperationResult[TRow, TKey] =
    repository.delete(id)

  def getById(id : TKey) : Option[TRow] = repository.getById(id)
}
