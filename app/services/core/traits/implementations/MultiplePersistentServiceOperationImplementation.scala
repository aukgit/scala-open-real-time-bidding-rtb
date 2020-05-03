package services.core.traits.implementations

import services.core.traits.MultiplePersistentServiceOperation
import shared.com.ortb.model.results.RepositoryOperationResultsModel
import shared.com.ortb.model.wrappers.persistent.EntityWrapperModel

trait MultiplePersistentServiceOperationImplementation[TTable, TRow, TKey]
  extends MultiplePersistentServiceOperation[TTable, TRow, TKey] {

  override def addEntities(
    entity   : TRow,
    addTimes : Int) : RepositoryOperationResultsModel[TRow, TKey] =
    repository.addEntities(entity, addTimes)

  override def addEntities(
    entities : Iterable[TRow]) : RepositoryOperationResultsModel[TRow, TKey] =
    repository.addEntities(entities)

  override def addOrUpdateEntities(
    entityWrappers : Iterable[EntityWrapperModel[TRow, TKey]]) :
  RepositoryOperationResultsModel[TRow, TKey] =
    repository.addOrUpdateEntities(entityWrappers)

  override def updateEntities(
    entityWrappers : Iterable[EntityWrapperModel[TRow, TKey]]) :
  RepositoryOperationResultsModel[TRow, TKey] =
    repository.updateEntities(entityWrappers)

  override def deleteEntities(
    entities : Iterable[TKey]) : RepositoryOperationResultsModel[TRow, TKey] =
    repository.deleteEntities(entities)
}
