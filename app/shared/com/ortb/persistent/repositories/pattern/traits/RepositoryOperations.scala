package shared.com.ortb.persistent.repositories.pattern.traits

import shared.com.ortb.enumeration.DatabaseActionType
import shared.com.ortb.model.persistent.EntityWrapper
import shared.com.ortb.model.results.RepositoryOperationResult
import shared.com.ortb.persistent.repositories.pattern.RepositoryBase
import shared.io.logger.AppLogger

trait RepositoryOperations[TTable, TRow, TKey]
    extends RepositoryOperationsBase[TRow] {
  this: RepositoryBase[TTable, TRow, TKey] =>

  def add(entity: TRow): RepositoryOperationResult[TRow, TKey] =
    toRegular(addAsync(entity), defaultTimeout)

  def update(entityId: TKey,
             entity: TRow): RepositoryOperationResult[TRow, TKey] =
    toRegular(updateAsync(entityId, entity), defaultTimeout)

  def delete(entityId: TKey): RepositoryOperationResult[TRow, TKey] =
    toRegular(deleteAsync(entityId), defaultTimeout)

  def addOrUpdate(entityId: TKey,
                  entity: TRow): RepositoryOperationResult[TRow, TKey] =
    toRegular(addOrUpdateAsync(entityId, entity), defaultTimeout)

  def addEntities(
    entities: Iterable[TRow]
  ): Iterable[RepositoryOperationResult[TRow, TKey]] = {
    if (entities == null || entities.isEmpty) {
      AppLogger.info(s"${headerMessage} No items passed for deleting.")

      return null
    }

    entities
      .map(entity => this.addAsync(entity))
      .map(
        entityResponseInFuture =>
          toRegular(entityResponseInFuture, defaultTimeout)
      )
  }

  def updateEntities(
    entityWrappers: Iterable[EntityWrapper[TRow, TKey]]
  ): Iterable[RepositoryOperationResult[TRow, TKey]] = {
    if (entityWrappers == null || entityWrappers.isEmpty) {
      AppLogger.info(
        s"${headerMessage} No items passed for multiple update operation."
      )

      return null
    }

    entityWrappers
      .map(
        entityWrapper =>
          updateAsync(entityWrapper.entityId, entityWrapper.entity)
      )
      .map(responseInFuture => toRegular(responseInFuture, defaultTimeout))
  }

  def deleteEntities(
    entities: Iterable[TKey]
  ): Iterable[RepositoryOperationResult[TRow, TKey]] = {
    val responses = deleteEntitiesAsync(entities)

    if (responses == null || responses.isEmpty) {
      return null
    }

    responses.map(futureResponse => toRegular(futureResponse, defaultTimeout))
  }

  def addEntities(
    entity: TRow,
    addTimes: Int
  ): Iterable[RepositoryOperationResult[TRow, TKey]] = {
    if (entity == null) {
      AppLogger.info(s"${headerMessage} No items passed for multiple adding.")

      return null
    }

    addEntitiesAsync(entity, addTimes)
      .map(futureResponse => toRegular(futureResponse, defaultTimeout))
  }

  def addOrUpdateEntities(
    entityWrappers: Iterable[EntityWrapper[TRow, TKey]]
  ): Iterable[RepositoryOperationResult[TRow, TKey]] = {
    if (entityWrappers == null || entityWrappers.isEmpty) {
      AppLogger.info(
        s"${headerMessage} No items passed for multiple ${DatabaseActionType.AddOrUpdate}."
      )

      return null
    }

    addOrUpdateEntitiesAsync(entityWrappers)
      .map(responseInFuture => toRegular(responseInFuture, defaultTimeout))
  }
}
