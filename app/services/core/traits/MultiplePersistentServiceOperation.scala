package services.core.traits

import shared.com.ortb.model.results.RepositoryOperationResultsModel
import shared.com.ortb.model.wrappers.persistent.EntityWrapperModel

trait MultiplePersistentServiceOperation[TTable, TRow, TKey]
  extends BasicPersistentServiceCore[TTable, TRow, TKey] {
  def addEntities(
    entity   : TRow,
    addTimes : Int) : RepositoryOperationResultsModel[TRow, TKey] = ???

  def addEntities(
    entities : Iterable[TRow]) : RepositoryOperationResultsModel[TRow, TKey] = ???

  def addOrUpdateEntities(
    entityWrappers : Iterable[EntityWrapperModel[TRow, TKey]]) : RepositoryOperationResultsModel[TRow, TKey] = ???

  def updateEntities(
    entityWrappers : Iterable[EntityWrapperModel[TRow, TKey]]) : RepositoryOperationResultsModel[TRow, TKey] = ???

  def deleteEntities(
    entities : Iterable[TKey]) : RepositoryOperationResultsModel[TRow, TKey] = ???
}
