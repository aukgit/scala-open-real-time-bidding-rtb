package shared.com.ortb.persistent.repositories.pattern.traits

import shared.com.ortb.enumeration.DatabaseActionType
import shared.com.ortb.model.persistent.EntityWrapper
import slick.jdbc.SQLiteProfile.api._
import shared.com.ortb.model.results.RepositoryOperationResult
import shared.com.ortb.persistent.repositories.pattern.RepositoryBase
import shared.io.AppLogger
import slick.dbio.{Effect, NoStream}
import slick.sql.FixedSqlAction

import scala.collection.mutable.ListBuffer
import scala.concurrent.Future

trait RepositoryOperationsAsync[TTable, TRow, TKey]
    extends RepositoryOperationsBase[TRow] {
  this: RepositoryBase[TTable, TRow, TKey] =>

  def getAddAction(entity: TRow): FixedSqlAction[TRow, NoStream, Effect.Write]

  def getDeleteAction(
    entityId: TKey
  ): FixedSqlAction[Int, NoStream, Effect.Write]

  def deleteAsync(
    entityId: TKey
  ): Future[RepositoryOperationResult[TRow, TKey]] = {
    val entity = getById(entityId)
    val actionType = DatabaseActionType.Delete

    try {
      val deleteAction = getDeleteAction(entityId)

      return this.saveAsync(
        entity = entity,
        dbAction = deleteAction,
        actionType
      )
    } catch {
      case e: Exception =>
        AppLogger.error(
          e,
          s"${headerMessage} Delete failed on [id:$entityId, entity: $entity]"
        )
    }

    getEmptyResponseForInFuture(actionType)
  }

  def addOrUpdateAsync(
    entityId: TKey,
    entity: TRow
  ): Future[RepositoryOperationResult[TRow, TKey]] = {
    if (isExists(entityId)) {
      // update
      return updateAsync(entityId, entity)
    }

    // add
    addAsync(entity)
  }

  def addAsync(entity: TRow): Future[RepositoryOperationResult[TRow, TKey]] = {
    val actionType = DatabaseActionType.Create

    try {
      val action = getAddAction(entity)

      return this.saveAsync(dbAction = action, DatabaseActionType.Create)
    } catch {
      case e: Exception =>
        AppLogger.error(e, s"${headerMessage} Add failed on [entity: $entity]")
    }

    getEmptyResponseForInFuture(actionType)
  }

  /**
    * if entityId is not matching with given entity id then recreates new entity and set the id given and then perform
    * the action.
    *
    * @param entityId
    * @param entity
    *
    * @return
    */
  def updateAsync(
    entityId: TKey,
    entity: TRow
  ): Future[RepositoryOperationResult[TRow, TKey]] = {
    val actionType = DatabaseActionType.Update

    try {
      if (entityId != getEntityId(Some(entity))) {
        val entity2 = setEntityId(Some(entityId), Some(entity))

        return this.saveAsync(
          entity = entity2,
          dbAction = getQueryById(entityId).update(entity2.get),
          DatabaseActionType.Update
        )
      }

      return this.saveAsync(
        entity = Some(entity),
        dbAction = getQueryById(entityId).update(entity),
        DatabaseActionType.Update
      )
    } catch {
      case e: Exception =>
        AppLogger.error(
          e,
          s"${headerMessage} Update failed on [id:$entityId, entity: $entity]"
        )
    }

    getEmptyResponseForInFuture(actionType)
  }

  def deleteEntitiesAsync(
    entities: Iterable[TKey]
  ): Iterable[Future[RepositoryOperationResult[TRow, TKey]]] = {
    if (entities == null || entities.isEmpty) {
      AppLogger.info(s"${headerMessage} No items passed for multiple deleting.")

      return null
    }

    entities.map(entity => this.deleteAsync(entity))
  }

  def addEntitiesAsync(
    entity: TRow,
    addTimes: Int
  ): Iterable[Future[RepositoryOperationResult[TRow, TKey]]] = {
    if (entity == null) {
      AppLogger.info(s"${headerMessage} No items passed for multiple adding.")

      return null
    }

    val list = ListBuffer[Future[RepositoryOperationResult[TRow, TKey]]]()

    for (i <- 0 until addTimes) {
      val response = this.addAsync(entity)
      list += response
    }

    list
  }

  def addOrUpdateEntitiesAsync(
    entityWrappers: Iterable[EntityWrapper[TRow, TKey]]
  ): Iterable[Future[RepositoryOperationResult[TRow, TKey]]] = {
    if (entityWrappers == null || entityWrappers.isEmpty) {
      AppLogger.info(
        s"${headerMessage} No items passed for multiple ${DatabaseActionType.AddOrUpdate}."
      )

      return null
    }

    entityWrappers.map(
      entityWrapper =>
        addOrUpdateAsync(entityWrapper.entityId, entityWrapper.entity)
    )
  }
}
