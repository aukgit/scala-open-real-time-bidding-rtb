package shared.com.repository.traits.implementions.operations.mutations

import shared.com.ortb.model.results.RepositoryOperationResult
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
  ) : Iterable[RepositoryOperationResult[TRow, TKey]] = {
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
}
