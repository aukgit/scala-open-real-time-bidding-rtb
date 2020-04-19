package services.core.traits.implementations

import services.core.traits.MultiplePersistentServiceOperation
import shared.com.ortb.model.repository.response.RepositoryOperationResultModel
import shared.com.ortb.model.wrappers.persistent.EntityWrapper

trait MultiplePersistentServiceOperationImplementation[TTable, TRow, TKey]
  extends MultiplePersistentServiceOperation[TTable, TRow, TKey] {

  def addEntities(
    entities : Iterable[TRow]
  ) : Iterable[RepositoryOperationResultModel[TRow, TKey]] =
    repository.addEntities(entities)

  def addEntities(
    entity   : TRow,
    addTimes : Int
  ) : Iterable[RepositoryOperationResultModel[TRow, TKey]] =
    repository.addEntities(entity, addTimes)

  def deleteEntities(
    entities : Iterable[TKey]
  ) : Iterable[RepositoryOperationResultModel[TRow, TKey]] =
    repository.deleteEntities(entities)

  def updateEntities(
    entityWrappers : Iterable[EntityWrapper[TRow, TKey]]
  ) : Iterable[RepositoryOperationResultModel[TRow, TKey]] =
    repository.updateEntities(entityWrappers)
}
