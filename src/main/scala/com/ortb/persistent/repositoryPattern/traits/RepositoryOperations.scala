package com.ortb.persistent.repositoryPattern.traits

import com.ortb.model.results.RepositoryOperationResult
import com.ortb.persistent.repositoryPattern.Repository

trait RepositoryOperations[TTable, TRow, TKey] extends RepositoryOperationsBase[TRow] {
  this: Repository[TTable, TRow, TKey] =>

  def add(entity: TRow): RepositoryOperationResult[TRow, TKey] =
    toRegular(addAsync(entity), defaultTimeout)

  def update(entityId: TKey, entity: TRow): RepositoryOperationResult[TRow, TKey] =
    toRegular(updateAsync(entityId, entity), defaultTimeout)

  def delete(entityId: TKey, entity: TRow): RepositoryOperationResult[TRow, TKey]=
    toRegular(deleteAsync(entityId), defaultTimeout)

  def addOrUpdate(entityId: TKey, entity: TRow): RepositoryOperationResult[TRow, TKey] =
    toRegular(addOrUpdateAsync(entityId, entity), defaultTimeout)
}
