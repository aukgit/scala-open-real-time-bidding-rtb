package shared.com.repository.traits.implementions.operations.mutations

import shared.com.ortb.enumeration.DatabaseActionType
import shared.com.ortb.model.repository.response.{RepositoryOperationResultModel, RepositoryOperationResultsModel}
import shared.com.ortb.model.wrappers.persistent.EntityWrapper
import shared.com.repository.RepositoryBase
import shared.com.repository.traits.operations.mutations.RepositoryUpdateOperations
import shared.io.helpers.BasicAdapterHelper
import shared.io.loggers.AppLogger

trait RepositoryUpdateOperationsImplementation[TTable, TRow, TKey]
  extends RepositoryUpdateOperations[TTable, TRow, TKey] {
  this : RepositoryBase[TTable, TRow, TKey] =>
  def update(
    entityId     : TKey,
    entity       : TRow) : RepositoryOperationResultModel[TRow, TKey] =
    toRegular(updateAsync(entityId, entity), defaultTimeout)

  def updateEntities(
    entityWrappers : Iterable[EntityWrapper[TRow, TKey]]
  ) : RepositoryOperationResultsModel[TRow, TKey] = {
    if (entityWrappers == null || entityWrappers.isEmpty) {
      AppLogger.info(s"${headerMessage} No items passed for multiple updates.")

      return null
    }

    val responseEntityWrappers = entityWrappers.map(
      entityWrapper => {
        this.updateAsync(
          entityId = entityWrapper.entityId,
          entity = entityWrapper.entity
        )
      });

    BasicAdapterHelper.repositoryAdapter.fromRepositoryOperationResultModelsToRepositoryOperationResultsModel(
      responseEntityWrappers,
      databaseActionType = DatabaseActionType.Update
    )
  }
}
