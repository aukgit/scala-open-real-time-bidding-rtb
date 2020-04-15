package com.ortb.persistent.repositoryPattern.traits

import com.ortb.enumeration.DatabaseActionType
import slick.jdbc.SQLiteProfile.api._
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

  def getDeleteAction(entityId : TKey) : FixedSqlAction[Int, NoStream, Effect.Write]

  def deleteAsync(entityId : TKey) :
  Future[RepositoryOperationResult[TRow, TKey]] = {
    val entity     = getById(entityId)
    val actionType = DatabaseActionType.Delete

    try {
      val deleteAction = getDeleteAction(entityId)

      return this.saveAsync(
        entity = entity,
        dbAction = deleteAction,
        actionType)
    }
    catch {
      case e : Exception => AppLogger.error(
        e,
        s"Delete failed on [id:$entityId, entity: $entity]")
    }

    getEmptyResponse(actionType)
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

  def addAsync(entity : TRow) : Future[RepositoryOperationResult[TRow, TKey]] = {
    val actionType = DatabaseActionType.Create

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

    getEmptyResponse(actionType)
  }

  /**
   * if entityId is not matching with given entity id then recreates new entity and set the id given and then perform
   * the action.
   *
   *
   *
   * @param entityId
   * @param entity
   *
   * @return
   */
  def updateAsync(entityId : TKey, entity : TRow) : Future[RepositoryOperationResult[TRow, TKey]] = {
    val actionType = DatabaseActionType.Update

    try {
      if (entityId != getEntityId(Some(entity))) {
        val entity2 = setEntityId(Some(entityId), Some(entity))

        return this.saveAsync(
          entity = entity2,
          dbAction = getQueryById(entityId).update(entity2.get),
          DatabaseActionType.Update)
      }

      return this.saveAsync(
        entity = Some(entity),
        dbAction = getQueryById(entityId).update(entity),
        DatabaseActionType.Update)
    }
    catch {
      case e : Exception => AppLogger.error(
        e,
        s"Update failed on [id:$entityId, entity: $entity]")
    }

    getEmptyResponse(actionType)
  }
}
