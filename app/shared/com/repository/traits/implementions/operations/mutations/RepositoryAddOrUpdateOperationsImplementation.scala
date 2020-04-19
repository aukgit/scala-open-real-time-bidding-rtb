package shared.com.repository.traits.implementions.operations.mutations

import shared.com.ortb.enumeration.DatabaseActionType
import shared.com.ortb.model.results.{RepositoryOperationResult, RepositoryOperationResults}
import shared.com.ortb.model.wrappers.persistent.EntityWrapper
import shared.com.repository.RepositoryBase
import shared.com.repository.traits.operations.mutations.RepositoryAddOrUpdateOperations
import shared.io.loggers.AppLogger

trait RepositoryAddOrUpdateOperationsImplementation[TTable, TRow, TKey]
  extends RepositoryAddOrUpdateOperations[TTable, TRow, TKey] {
  this : RepositoryBase[TTable, TRow, TKey] =>
  def addOrUpdate(
    entityId         : TKey,
    entity           : TRow) : RepositoryOperationResult[TRow, TKey] =
    toRegular(addOrUpdateAsync(entityId, entity), defaultTimeout)
//
//  def addOrUpdateEntities(
//    entityWrappers : Iterable[EntityWrapper[TRow, TKey]]
//  ) : Iterable[RepositoryOperationResult[TRow, TKey]] = {
//    if (entityWrappers == null || entityWrappers.isEmpty) {
//      AppLogger.info(
//        s"${headerMessage} No items passed for multiple ${DatabaseActionType.AddOrUpdate}."
//      )
//
//      return null
//    }
//
//    addOrUpdateEntitiesAsync(entityWrappers)
//      .map(responseInFuture => toRegular(responseInFuture, defaultTimeout))
//  }


  def addOrUpdateEntities(
    entityWrappers : Iterable[EntityWrapper[TRow, TKey]]
  ) : RepositoryOperationResults[TRow, TKey] = {
    if (entityWrappers == null || entityWrappers.isEmpty) {
      AppLogger.info(
        s"${headerMessage} No items passed for multiple ${DatabaseActionType.AddOrUpdate}."
      )

      return null
    }

    val wrapperResponses = entityWrappers.map(
      entityWrapper =>
        addOrUpdateAsync(entityWrapper.entityId, entityWrapper.entity)
    ).map(responseInFuture => {
      val response = toRegular(responseInFuture, defaultTimeout)
      EntityWrapper(response.entityId, response.entity)
    })

    RepositoryOperationResults(wrapperResponses.nonEmpty, wrapperResponses, DatabaseActionType.AddOrUpdate)
  }
}
