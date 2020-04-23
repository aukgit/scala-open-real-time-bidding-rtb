package shared.com.repository.traits.implementions.operations.mutations

import shared.com.ortb.enumeration.DatabaseActionType
import shared.com.ortb.model.attributes.GenericResponseAttributesModel
import shared.com.ortb.model.repository.response.RepositoryOperationResultsModel
import shared.com.ortb.model.results.{ RepositoryOperationResultModel, RepositoryOperationResultsModel }
import shared.com.ortb.model.wrappers.persistent.EntityWrapper
import shared.com.repository.RepositoryBase
import shared.com.repository.traits.operations.mutations.RepositoryAddOrUpdateOperations
import shared.io.helpers.BasicAdapterHelper
import shared.io.loggers.AppLogger

trait RepositoryAddOrUpdateOperationsImplementation[TTable, TRow, TKey]
  extends RepositoryAddOrUpdateOperations[TTable, TRow, TKey] {
  this : RepositoryBase[TTable, TRow, TKey] =>
  def addOrUpdate(
    entityId         : TKey,
    entity           : TRow) : RepositoryOperationResultModel[TRow, TKey] =
    toRegular(addOrUpdateAsync(entityId, entity), defaultTimeout)

  def addOrUpdateEntities(
    entityWrappers : Iterable[EntityWrapper[TRow, TKey]]
  ) : RepositoryOperationResultsModel[TRow, TKey] = {
    if (entityWrappers == null || entityWrappers.isEmpty) {
      AppLogger.info(
        s"${headerMessage} No items passed for multiple ${DatabaseActionType.AddOrUpdate}."
      )

      return null
    }

    val responsesEntityWrappers = entityWrappers.map(
      entityWrapper =>
        addOrUpdateAsync(entityWrapper.entityId, entityWrapper.entity)
    )

    BasicAdapterHelper.repositoryAdapter.fromRepositoryOperationResultModelsToRepositoryOperationResultsModel(
      responsesEntityWrappers,
      databaseActionType = DatabaseActionType.AddOrUpdate
    )
  }
}
