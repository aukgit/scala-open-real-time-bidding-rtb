package services.core.traits.implementations

import services.core.traits.MultiplePersistentServiceOperation
import shared.com.ortb.model.results.RepositoryOperationResult
import shared.com.ortb.model.wrappers.persistent.EntityWrapper

trait MultiplePersistentServiceOperationImplementation[TTable, TRow, TKey]
  extends MultiplePersistentServiceOperation[TTable, TRow, TKey] {

  def addEntities(
    entities : Iterable[TRow]
  ) : Iterable[RepositoryOperationResult[TRow, TKey]] =
    repository.addEntities(entities)

  def addEntities(
    entity   : TRow,
    addTimes : Int
  ) : Iterable[RepositoryOperationResult[TRow, TKey]] =
    repository.addEntities(entity, addTimes)

  def deleteEntities(
    entities : Iterable[TKey]
  ) : Iterable[RepositoryOperationResult[TRow, TKey]] =
    repository.deleteEntities(entities)

  def updateEntities(
    entityWrappers : Iterable[EntityWrapper[TRow, TKey]]
  ) : Iterable[RepositoryOperationResult[TRow, TKey]] =
    repository.updateEntities(entityWrappers)
}
