package shared.com.repository.traits.implementions.operations.mutations

import shared.com.ortb.model.results.{RepositoryOperationResult, RepositoryOperationResults}
import shared.com.repository.RepositoryBase
import shared.com.repository.traits.operations.mutations.RepositoryDeleteOperations
import slick.dbio.{Effect, NoStream}
import slick.sql.FixedSqlAction

trait RepositoryDeleteOperationsImplementation[TTable, TRow, TKey]
  extends RepositoryDeleteOperations[TTable, TRow, TKey] {
  this : RepositoryBase[TTable, TRow, TKey] =>

  def getDeleteAction(
    entityId : TKey
  ) : FixedSqlAction[Int, NoStream, Effect.Write]

  def delete(entityId     : TKey) : RepositoryOperationResult[TRow, TKey] =
    toRegular(deleteAsync(entityId), defaultTimeout)

  def deleteEntities(
    entities      : Iterable[TKey]
  ) : RepositoryOperationResults[TRow, TKey] = {
    val responses = deleteEntitiesAsync(entities)

    if (responses == null || responses.isEmpty) {
      return null
    }

    responses.map(futureResponse => toRegular(futureResponse, defaultTimeout))
  }
}
