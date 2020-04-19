package shared.com.repository.traits.implementions.operations.mutations

import shared.com.ortb.model.results.RepositoryOperationResult
import shared.com.repository.RepositoryBase
import shared.com.repository.traits.operations.mutations.RepositoryAddOperations
import shared.io.loggers.AppLogger

trait RepositoryAddOperationsImplementation[TTable, TRow, TKey]
  extends RepositoryAddOperations[TTable, TRow, TKey] {
  this : RepositoryBase[TTable, TRow, TKey] =>
  def add(entity : TRow) : RepositoryOperationResult[TRow, TKey] =
    toRegular(addAsync(entity), defaultTimeout)

  def addEntities(
    entity : TRow,
    addTimes : Int
  ) : Iterable[RepositoryOperationResult[TRow, TKey]] = {
    if (entity == null) {
      AppLogger.info(s"${headerMessage} No items passed for multiple adding.")

      return null
    }

    addEntitiesAsync(entity, addTimes)
      .map(futureResponse => toRegular(futureResponse, defaultTimeout))
  }

  def addEntities(
    entities : Iterable[TRow]
  ) : Iterable[RepositoryOperationResult[TRow, TKey]] = {
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
}
