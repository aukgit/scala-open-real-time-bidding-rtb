package com.ortb.persistent.repositoryPattern.traits

import com.ortb.model.results.RepositoryOperationResult
import com.ortb.persistent.repositoryPattern.Repository

trait RepositoryOperations[TTable, TRow, TKey] extends RepositoryOperationsBase[TRow] {
  this: Repository[TTable, TRow, TKey] =>

  def add(entityId: TKey, entity: TRow): RepositoryOperationResult[TRow] =
    toRegular(addAsync(entityId, entity), defaultTimeout)

  def update(entityId: TKey, entity: TRow): RepositoryOperationResult[TRow] =
    toRegular(updateAsync(entityId, entity), defaultTimeout)

  def delete(entityId: TKey, entity: TRow): RepositoryOperationResult[TRow]=
    toRegular(deleteAsync(entityId, entity), defaultTimeout)

  def addOrUpdate(entityId: TKey, entity: TRow): RepositoryOperationResult[TRow] =
    toRegular(addOrUpdateAsync(entityId, entity), defaultTimeout)
}
