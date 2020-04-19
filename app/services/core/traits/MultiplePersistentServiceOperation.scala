package services.core.traits

import shared.com.ortb.model.repository.response.RepositoryOperationResultModel
import shared.com.ortb.model.wrappers.persistent.EntityWrapper

trait MultiplePersistentServiceOperation[TTable, TRow, TKey]
    extends BasicPersistentServiceCore[TTable, TRow, TKey] {

  def addEntities(
      entities: Iterable[TRow]
  ): Iterable[RepositoryOperationResultModel[TRow, TKey]]

  def addEntities(
      entity: TRow,
      addTimes: Int
  ): Iterable[RepositoryOperationResultModel[TRow, TKey]]

  def deleteEntities(
      entities: Iterable[TKey]
  ): Iterable[RepositoryOperationResultModel[TRow, TKey]]

  def updateEntities(
      entityWrappers: Iterable[EntityWrapper[TRow, TKey]]
  ): Iterable[RepositoryOperationResultModel[TRow, TKey]]
}
