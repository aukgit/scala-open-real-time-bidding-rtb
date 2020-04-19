package shared.com.repository.traits.implementions.operations.mutations

import shared.com.ortb.enumeration.DatabaseActionType
import shared.com.ortb.model.results.{RepositoryOperationResult, RepositoryOperationResults}
import shared.com.ortb.model.wrappers.persistent.EntityWrapper
import shared.com.repository.RepositoryBase
import shared.com.repository.traits.operations.mutations.RepositoryAddOperations
import shared.io.loggers.AppLogger
import slick.dbio.{Effect, NoStream}
import slick.sql.FixedSqlAction

import scala.concurrent.Future

trait RepositoryAddOperationsImplementation[TTable, TRow, TKey]
  extends RepositoryAddOperations[TTable, TRow, TKey] {
  this : RepositoryBase[TTable, TRow, TKey] =>
  def getAddAction(entity : TRow) : FixedSqlAction[TRow, NoStream, Effect.Write]

  def add(entity : TRow) : RepositoryOperationResult[TRow, TKey] =
    toRegular(addAsync(entity), defaultTimeout)

  def addEntities(
    entity : TRow,
    addTimes : Int
  ) : RepositoryOperationResults[TRow, TKey] = {
    if (entity == null) {
      AppLogger.info(s"${headerMessage} No items passed for multiple adding.")

      return null
    }

    val list = new Array[Future[RepositoryOperationResult[TRow, TKey]]](addTimes)

    for (i <- 0 until addTimes) {
      list(i) = this.addAsync(entity)
    }

    val responsesEntityWrappers = list.map(responseInFuture => {
      val response = toRegular(responseInFuture, defaultTimeout)
      EntityWrapper(response.entityId, response.entity)
    })

    RepositoryOperationResults(
      responsesEntityWrappers.nonEmpty,
      responsesEntityWrappers,
      DatabaseActionType.Create)
  }

  def addEntities(
    entities : Iterable[TRow]
  ) : RepositoryOperationResults[TRow, TKey] = {
    if (entities == null || entities.isEmpty) {
      AppLogger.info(s"${headerMessage} No items passed for deleting.")

      return null
    }

    val wrappers = entities
      .map(entity => this.addAsync(entity))
      .map(
        entityResponseInFuture => {
          val response = toRegular(entityResponseInFuture, defaultTimeout)

          EntityWrapper(response.entityId, response.entity)
        })

    RepositoryOperationResults(wrappers.nonEmpty, wrappers, actionType = DatabaseActionType.Create)
  }
}
