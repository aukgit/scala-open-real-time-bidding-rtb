package com.ortb.persistent.repositoryPattern.traits

import com.ortb.enumeration.DatabaseActionType
import slick.jdbc.SQLiteProfile.api._
import com.ortb.implicits.ImplicitsDefinitions.anyRefCaller
import com.ortb.model.results.RepositoryOperationResult
import com.ortb.persistent.repositoryPattern.Repository
import io.AppLogger
import slick.dbio.{NoStream, Effect}
import slick.sql.FixedSqlAction

import scala.concurrent.Future

trait RepositoryOperationsAsync[TTable, TRow, TKey]
  extends
  RepositoryOperationsBase[TRow] {
  this : Repository[TTable, TRow, TKey] =>

  def getAddAction(entity : TRow) : FixedSqlAction[TRow, NoStream, Effect.Write]

  def addAsync(entity : TRow) : Future[RepositoryOperationResult[TRow, TKey]] = {
    try {
      val action = getAddAction(entity)
      return this.saveAsync(
        dbAction = action,
        DatabaseActionType.Create)
    }
    catch {
      case e : Exception => AppLogger.error(
        e,
        s"Add failed on [entity: $entity]")
    }

    getEmptyResponse
  }

  def deleteAsync(entityId : TKey) :
  Future[RepositoryOperationResult[TRow, TKey]] = {
    val entity = getById(entityId)

    try {
      return this.saveAsync(
        entity = entity,
        dbAction = getQueryByIdSingle(entityId)
          .call("delete")
          .asInstanceOf[FixedSqlAction[Int, NoStream, Effect.Write]],
        DatabaseActionType.Delete)
    }
    catch {
      case e : Exception => AppLogger.error(
        e,
        s"Delete failed on [id:$entityId, entity: $entity]")
    }

    getEmptyResponse
  }

  def updateAsync(entityId : TKey, entity : TRow) : Future[RepositoryOperationResult[TRow, TKey]] = {
    try {
      return this.saveAsync(
        entity = entity,
        dbAction = getQueryByIdSingle(entityId).update(entity),
        DatabaseActionType.Update)
    }
    catch {
      case e : Exception => AppLogger.error(
        e,
        s"Update failed on [id:$entityId, entity: $entity]")
    }

    getEmptyResponse
  }

  def addOrUpdateAsync(entityId : TKey, entity : TRow) :
  Future[RepositoryOperationResult[TRow, TKey]] = {
    if (isExists(entityId)) {
      // update
      return updateAsync(entityId, entity)
    }

    // add
    addAsync(entity)
  }
}
