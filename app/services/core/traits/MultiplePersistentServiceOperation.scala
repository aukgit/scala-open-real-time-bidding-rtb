package services.core.traits

import shared.com.ortb.model.wrappers.persistent.EntityWrapper
import shared.com.ortb.model.results.RepositoryOperationResult
import shared.com.ortb.persistent.repositories.pattern.RepositoryBase

trait MultiplePersistentServiceOperation[TTable, TRow, TKey] {
  val repository: RepositoryBase[TTable, TRow, TKey]

  def addEntities(
    entities: Iterable[TRow]
  ): Iterable[RepositoryOperationResult[TRow, TKey]] =
    repository.addEntities(entities)

  def addEntities(
    entity: TRow,
    addTimes: Int
  ): Iterable[RepositoryOperationResult[TRow, TKey]] =
    repository.addEntities(entity, addTimes)

  def deleteEntities(
    entities: Iterable[TKey]
  ): Iterable[RepositoryOperationResult[TRow, TKey]] =
    repository.deleteEntities(entities)

  def updateEntities(
    entityWrappers: Iterable[EntityWrapper[TRow, TKey]]
  ): Iterable[RepositoryOperationResult[TRow, TKey]] =
    repository.updateEntities(entityWrappers)
}
