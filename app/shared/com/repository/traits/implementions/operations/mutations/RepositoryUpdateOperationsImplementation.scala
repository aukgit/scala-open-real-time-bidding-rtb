package shared.com.repository.traits.implementions.operations.mutations

import shared.com.ortb.enumeration.DatabaseActionType
import shared.com.ortb.model.results.{RepositoryOperationResult, RepositoryOperationResults}
import shared.com.ortb.model.wrappers.persistent.EntityWrapper
import shared.com.repository.RepositoryBase
import shared.com.repository.traits.operations.mutations.RepositoryUpdateOperations
import shared.io.loggers.AppLogger

trait RepositoryUpdateOperationsImplementation[TTable, TRow, TKey]
  extends RepositoryUpdateOperations[TTable, TRow, TKey] {
  this : RepositoryBase[TTable, TRow, TKey] =>
  def update(
    entityId     : TKey,
    entity       : TRow) : RepositoryOperationResult[TRow, TKey] =
    toRegular(updateAsync(entityId, entity), defaultTimeout)

  def updateEntities(
    entityWrappers : Iterable[EntityWrapper[TRow, TKey]]
  ) : RepositoryOperationResults[TRow, TKey] = {
    if (entityWrappers == null || entityWrappers.isEmpty) {
      AppLogger.info(s"${headerMessage} No items passed for multiple updates.")

      return null
    }

    val length = entityWrappers.size

    val responseEntityWrappers = entityWrappers.map(
      entityWrapper => {
        this.updateAsync(
          entityId = entityWrapper.entityId,
          entity = entityWrapper.entity
        )
      }).map(repositoryResultInFuture => {
      val responseInNonFuture : RepositoryOperationResult[TRow, TKey] = toRegular(repositoryResultInFuture,
        defaultTimeout)

      EntityWrapper(responseInNonFuture.entityId.get, responseInNonFuture.entity.get)
    })

    val isSuccess = length == responseEntityWrappers.size

    RepositoryOperationResults[TRow, TKey](
      isSuccess,
      responseEntityWrappers,
      actionType = DatabaseActionType.Update)
  }
}
