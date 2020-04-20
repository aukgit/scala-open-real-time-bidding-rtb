package services.core.traits.implementations

import services.core.traits.MultiplePersistentServiceOperation
import shared.com.ortb.model.repository.response.{RepositoryOperationResultModel, RepositoryOperationResultsModel}
import shared.com.ortb.model.wrappers.persistent.EntityWrapper
import shared.com.repository.RepositoryBase

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
    entityWrappers : Iterable[EntityWrapper[TRow, TKey]]) :
  RepositoryOperationResultsModel[TRow, TKey] =
    repository.addOrUpdateEntities(entityWrappers)

  override def updateEntities(
    entityWrappers : Iterable[EntityWrapper[TRow, TKey]]) :
  RepositoryOperationResultsModel[TRow, TKey] =
    repository.updateEntities(entityWrappers)

  override def deleteEntities(
    entities : Iterable[TKey]) : RepositoryOperationResultsModel[TRow, TKey] =
    repository.deleteEntities(entities)
}
