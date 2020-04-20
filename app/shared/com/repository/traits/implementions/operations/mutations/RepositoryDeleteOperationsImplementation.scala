package shared.com.repository.traits.implementions.operations.mutations

import shared.com.ortb.enumeration.DatabaseActionType
import shared.com.ortb.model.repository.response.{RepositoryOperationResultModel, RepositoryOperationResultsModel}
import shared.com.repository.RepositoryBase
import shared.com.repository.traits.operations.mutations.RepositoryDeleteOperations
import shared.io.helpers.BasicAdapterHelper
import slick.dbio.{Effect, NoStream}
import slick.sql.FixedSqlAction

trait RepositoryDeleteOperationsImplementation[TTable, TRow, TKey]
  extends RepositoryDeleteOperations[TTable, TRow, TKey] {
  this : RepositoryBase[TTable, TRow, TKey] =>
  def delete(entityId     : TKey) : RepositoryOperationResultModel[TRow, TKey] =
    toRegular(deleteAsync(entityId), defaultTimeout)

  def deleteEntities(
    entities      : Iterable[TKey]
  ) : RepositoryOperationResultsModel[TRow, TKey] = {
    if (entities == null || entities.isEmpty) {
      return null
    }

    val responses = entities.map(id => deleteAsync(id))

    BasicAdapterHelper.fromRepositoryOperationResultModelsToRepositoryOperationResultsModel(
      responses,
      databaseActionType = DatabaseActionType.Delete
    )
  }
}
