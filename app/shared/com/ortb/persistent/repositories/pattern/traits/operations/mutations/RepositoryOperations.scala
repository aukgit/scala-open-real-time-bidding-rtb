package shared.com.ortb.persistent.repositories.pattern.traits.operations.mutations

import shared.com.ortb.model.results.RepositoryOperationResult
import shared.com.ortb.model.wrappers.persistent.EntityWrapper

trait RepositoryOperations[TTable, TRow, TKey]
  extends RepositoryOperationsBase[TRow] {

  def add(entity : TRow) : RepositoryOperationResult[TRow, TKey]

  def update(
    entityId : TKey,
    entity : TRow) : RepositoryOperationResult[TRow, TKey]

  def delete(entityId : TKey) : RepositoryOperationResult[TRow, TKey]

  def addOrUpdate(
    entityId : TKey,
    entity : TRow) : RepositoryOperationResult[TRow, TKey]

  def addEntities(
    entities : Iterable[TRow]
  ) : Iterable[RepositoryOperationResult[TRow, TKey]]

  def updateEntities(
    entityWrappers : Iterable[EntityWrapper[TRow, TKey]]
  ) : Iterable[RepositoryOperationResult[TRow, TKey]]

  def deleteEntities(
    entities : Iterable[TKey]
  ) : Iterable[RepositoryOperationResult[TRow, TKey]]

  def addEntities(
    entity : TRow,
    addTimes : Int
  ) : Iterable[RepositoryOperationResult[TRow, TKey]]

  def addOrUpdateEntities(
    entityWrappers : Iterable[EntityWrapper[TRow, TKey]]
  ) : Iterable[RepositoryOperationResult[TRow, TKey]]
}


