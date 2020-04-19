package shared.com.repository.traits.implementions.operations.mutations

import shared.com.ortb.model.repository.response.{RepositoryOperationResultModel, RepositoryOperationResultsModel}
import shared.com.repository.RepositoryBase
import shared.com.repository.traits.operations.mutations.RepositoryDeleteOperations
import slick.dbio.{Effect, NoStream}
import slick.sql.FixedSqlAction

import scala.concurrent.Future

trait RepositoryDeleteOperationsImplementation[TTable, TRow, TKey]
  extends RepositoryDeleteOperations[TTable, TRow, TKey] {
  this : RepositoryBase[TTable, TRow, TKey] =>

  def getDeleteAction(
    entityId : TKey
  ) : FixedSqlAction[Int, NoStream, Effect.Write]

  def delete(entityId     : TKey) : RepositoryOperationResultModel[TRow, TKey] =
    toRegular(deleteAsync(entityId), defaultTimeout)

  def deleteEntities(
    entities      : Iterable[TKey]
  ) : RepositoryOperationResultsModel[TRow, TKey] = {
    val responses : Iterable[Future[RepositoryOperationResultModel[TRow, TKey]]] = deleteEntitiesAsync(entities)

    if (responses == null || responses.isEmpty) {
      return null
    }

    responses.map(futureResponse =>
      toRegular(futureResponse, defaultTimeout))
  }
}
