package com.ortb.persistent.repositoryPattern.traits

import com.ortb.model.results.RepositoryOperationResult
import com.ortb.persistent.repositoryPattern.Repository

trait RepositoryOperations[TTable, TRow, TKey] extends RepositoryOperationsBase[TRow] {
  this: Repository[TTable, TRow, TKey] =>

  def add(entityId: TKey, entity: TRow): RepositoryOperationResult[TRow] =
    toRegular(addAsync(entityId, entity))

  def update(entityId: TKey, entity: TRow): RepositoryOperationResult[TRow] =
    toRegular(updateAsync(entityId, entity))

  def delete(entityId: TKey, entity: TRow): RepositoryOperationResult[TRow]=
    toRegular(deleteAsync(entityId, entity))

  def addOrUpdate(entityId: TKey, entity: TRow): RepositoryOperationResult[TRow] =
    toRegular(addOrUpdateAsync(entityId, entity))
}
