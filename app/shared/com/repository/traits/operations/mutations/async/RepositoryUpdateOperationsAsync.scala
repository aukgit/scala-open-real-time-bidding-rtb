package shared.com.repository.traits.operations.mutations.async

import shared.com.ortb.model.results.RepositoryOperationResultModel
import shared.com.repository.traits.operations.mutations.RepositoryOperationsBase

import scala.concurrent.Future

trait RepositoryUpdateOperationsAsync[TTable, TRow, TKey]
  extends RepositoryOperationsBase[TRow] {
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
  ) : Future[RepositoryOperationResultModel[TRow, TKey]]
}
