package services.traits

import shared.com.ortb.model.persistent.EntityWrapper
import shared.com.ortb.model.results.RepositoryOperationResult

trait MultiplePersistentServiceOperation[TTable, TRow, TKey] {
  def addEntities(
    entity: Iterable[TRow]
  ): Iterable[RepositoryOperationResult[TRow, TKey]]

  def addEntities(
    entity: TRow,
    addTimes: Int
  ): Iterable[RepositoryOperationResult[TRow, TKey]]

  def deleteEntities(
    entities: Iterable[TKey]
  ): Iterable[RepositoryOperationResult[TRow, TKey]]

  def updateEntities(
    entityWrappers: Iterable[EntityWrapper[TRow, TKey]]
  ): Iterable[RepositoryOperationResult[TRow, TKey]]
}
