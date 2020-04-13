package com.ortb.persistent.repositoryPattern.traits

import com.ortb.enumeration.DatabaseActionType
import com.ortb.model.results.RepositoryOperationResult
import com.ortb.persistent.repositoryPattern.Repository
import slick.lifted.AbstractTable
import slick.jdbc.SQLiteProfile.api._

import scala.concurrent.Future

trait RepositoryOperationsAsync[TTable <: AbstractTable[_], TRow <: Null, TKey]
  extends RepositoryOperationsBase[TRow] {
  this: Repository[TTable, TRow, TKey] =>

  def addAsync(entity: TRow): Future[RepositoryOperationResult[TRow]]

  def delete(entity: TRow): Future[RepositoryOperationResult[TRow]]

  def updateAsync(entityId: TKey, entity: TRow): Future[RepositoryOperationResult[TRow]] = {
    this.saveAsync(
      entity = entity,
      dbAction = getQueryById(entityId).take(1).update(entity),
      DatabaseActionType.Update
    )
  }

  def addOrUpdateAsync(entityId: TKey, entity: TRow): Future[RepositoryOperationResult[TRow]] = {
    if (isExists(entityId)) {
      // update
      return updateAsync(entityId, entity)
    }

    // add
    addAsync(entity)
  }
}
