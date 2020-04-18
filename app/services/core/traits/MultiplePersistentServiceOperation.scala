package services.core.traits

import shared.com.ortb.model.results.RepositoryOperationResult
import shared.com.ortb.model.wrappers.persistent.EntityWrapper

trait MultiplePersistentServiceOperation[TTable, TRow, TKey]
    extends BasicPersistentServiceCore[TTable, TRow, TKey] {

  def addEntities(
      entities: Iterable[TRow]
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
