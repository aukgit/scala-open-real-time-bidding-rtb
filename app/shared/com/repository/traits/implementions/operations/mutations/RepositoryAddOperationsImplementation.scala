package shared.com.repository.traits.implementions.operations.mutations

import shared.com.ortb.enumeration.DatabaseActionType
import shared.com.ortb.model.attributes.GenericResponseAttributesModel
import shared.com.ortb.model.repository.response.RepositoryOperationResultsModel
import shared.com.ortb.model.results.{ RepositoryOperationResultModel, RepositoryOperationResultsModel }
import shared.com.ortb.model.wrappers.persistent.EntityWrapper
import shared.com.repository.RepositoryBase
import shared.com.repository.traits.operations.mutations.RepositoryAddOperations
import shared.io.helpers.BasicAdapterHelper
import shared.io.loggers.AppLogger
import slick.dbio.{ Effect, NoStream }
import slick.sql.FixedSqlAction

import scala.concurrent.Future

trait RepositoryAddOperationsImplementation[TTable, TRow, TKey]
  extends RepositoryAddOperations[TTable, TRow, TKey] {
  this : RepositoryBase[TTable, TRow, TKey] =>
  def getAddAction(entity : TRow) : FixedSqlAction[TRow, NoStream, Effect.Write]

  def add(entity : TRow) : RepositoryOperationResultModel[TRow, TKey] =
    toRegular(addAsync(entity), defaultTimeout)

  def addEntities(
    entity : TRow,
    addTimes : Int
  ) : RepositoryOperationResultsModel[TRow, TKey] = {
    if (entity == null) {
      AppLogger.info(s"${headerMessage} No items passed for multiple adding.")

      return null
    }

    val list = new Array[Future[RepositoryOperationResultModel[TRow, TKey]]](addTimes)

    for (i <- 0 until addTimes) {
      list(i) = this.addAsync(entity)
    }

    BasicAdapterHelper.repositoryAdapter.fromRepositoryOperationResultModelsToRepositoryOperationResultsModel(
      list,
      databaseActionType = DatabaseActionType.Create
    )
  }

  def addEntities(
    entities : Iterable[TRow]
  ) : RepositoryOperationResultsModel[TRow, TKey] = {
    if (entities == null || entities.isEmpty) {
      AppLogger.info(s"${headerMessage} No items passed for deleting.")

      return null
    }

    val responsesEntityWrappers = entities
      .map(entity => this.addAsync(entity))

    BasicAdapterHelper.repositoryAdapter.fromRepositoryOperationResultModelsToRepositoryOperationResultsModel(
      responsesEntityWrappers,
      databaseActionType = DatabaseActionType.Create
    )
  }
}
