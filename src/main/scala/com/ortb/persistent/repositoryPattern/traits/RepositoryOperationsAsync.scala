package com.ortb.persistent.repositoryPattern.traits

import com.ortb.enumeration.DatabaseActionType
import slick.lifted.AbstractTable
import slick.jdbc.SQLiteProfile.api._
import com.ortb.implicits.ImplicitsDefinitions.anyRefCaller
import com.ortb.model.results.RepositoryOperationResult
import com.ortb.persistent.repositoryPattern.Repository
import io.AppLogger
import slick.dbio.{NoStream, Effect}
import slick.sql.FixedSqlAction

import scala.concurrent.Future

trait RepositoryOperationsAsync[TTable <: AbstractTable[_], TRow <: Null, TKey]
  extends RepositoryOperationsBase[TRow] {
  this: Repository[TTable, TRow, TKey] =>

  def addAsync(entity: TRow): Future[RepositoryOperationResult[TRow]]

  def deleteAsync(entityKey: TKey, entity: TRow):
  Future[RepositoryOperationResult[TRow]] = {
    try {
      return this.saveAsync(
        entityKey = entityKey,
        entity = entity,
        dbAction = getQueryByIdSingle(entityKey)
          .call("delete")
          .asInstanceOf[FixedSqlAction[Int, NoStream, Effect.Write]],
        isPerformActionOnExist = true,
        DatabaseActionType.Delete)
    } catch {
      case e: Exception => AppLogger.error(e,
        s"Delete failed on [id:$entityKey, entity: $entity]")
    }

    getEmptyResponse
  }

  def updateAsync(entityId: TKey, entity: TRow): Future[RepositoryOperationResult[TRow]] = {
    try {
      return this.saveAsync(
        entity = entity,
        dbAction = getQueryByIdSingle(entityId).update(entity),
        DatabaseActionType.Update
      )
    }
    catch {
      case e: Exception => AppLogger.error(e,
        s"Update failed on [id:$entityId, entity: $entity]")
    }

    getEmptyResponse
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
