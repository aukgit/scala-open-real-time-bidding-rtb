package com.ortb.persistent.repositoryPattern.traits

import com.ortb.enumeration.DatabaseActionType
import com.ortb.model.results.RepositoryOperationResult
import com.ortb.persistent.repositoryPattern.Repository
import com.ortb.persistent.schema.Tables
import slick.lifted.AbstractTable
import slick.jdbc.SQLiteProfile.api._

import scala.concurrent._
import io.AppLogger
import slick.jdbc.SQLiteProfile.api._
import slick.jdbc.JdbcActionComponent
import com.ortb.manager.AppManager
import com.ortb.model.results.RepositoryOperationResult
import com.ortb.persistent.repositoryPattern.Repository
import com.ortb.persistent.schema.Tables
import com.ortb.persistent.schema.Tables._
import slick.collection.heterogeneous.Zero.+
import slick.dbio._
import slick.lifted.Query

import scala.concurrent.Future

trait RepositoryOperationsAsync[TTable <: AbstractTable[_], TRow <: Null, TKey]
  extends RepositoryOperationsBase[TRow] {
  this: Repository[TTable, TRow, TKey] =>

  def addAsync(entity: TRow): Future[RepositoryOperationResult[TRow]]

  def deleteAsync(entityKey: TKey, entity: TRow):
  Future[RepositoryOperationResult[TRow]] = {
    this.saveAsync(
      entityKey = entityKey,
      entity = entity,
      dbAction = getQueryByIdSingle(entityKey).delete,
      isPerformActionOnExist = true,
      DatabaseActionType.Delete)
  }

  def updateAsync(entityId: TKey, entity: TRow): Future[RepositoryOperationResult[TRow]] = {
    this.saveAsync(
      entity = entity,
      dbAction = getQueryByIdSingle(entityId).update(entity),
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
