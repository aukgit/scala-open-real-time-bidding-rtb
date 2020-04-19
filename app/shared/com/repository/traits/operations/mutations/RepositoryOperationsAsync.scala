package shared.com.repository.traits.operations.mutations

import shared.com.ortb.model.results.{RepositoryOperationResult, RepositoryOperationResults}
import shared.com.ortb.model.wrappers.persistent.EntityWrapper
import slick.dbio.{Effect, NoStream}
import slick.sql.FixedSqlAction

import scala.concurrent.Future

trait RepositoryOperationsAsync[TTable, TRow, TKey]
  extends RepositoryOperationsBase[TRow] {

  def getAddAction(entity : TRow) : FixedSqlAction[TRow, NoStream, Effect.Write]



  def deleteAsync(
    entityId : TKey
  ) : Future[RepositoryOperationResult[TRow, TKey]]

  def addOrUpdateAsync(
    entityId : TKey,
    entity : TRow
  ) : Future[RepositoryOperationResult[TRow, TKey]]

  def addAsync(entity : TRow) : Future[RepositoryOperationResult[TRow, TKey]]

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
    entityId : TKey,
    entity : TRow
  ) : Future[RepositoryOperationResult[TRow, TKey]]
//
//  def deleteEntitiesAsync(
//    entities : Iterable[TKey]
//  ) : Future[RepositoryOperationResults[TRow, TKey]]
}
