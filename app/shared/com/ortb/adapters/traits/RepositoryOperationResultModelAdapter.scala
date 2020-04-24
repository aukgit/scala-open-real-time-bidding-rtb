package shared.com.ortb.adapters.traits

import shared.com.ortb.enumeration.DatabaseActionType.DatabaseActionType
import shared.com.ortb.model.results.{ RepositoryOperationResultModel, RepositoryOperationResultsModel }

import scala.concurrent.Future

trait RepositoryOperationResultModelAdapter {
  def getEmptyResponse[TRow, TKey](
    databaseActionType : DatabaseActionType) :
  RepositoryOperationResultModel[TRow, TKey]

  def getEmptyResultsResponse[TRow, TKey](
    databaseActionType : DatabaseActionType)
  : RepositoryOperationResultsModel[TRow, TKey]

  def fromRepositoryOperationResultModelsToRepositoryOperationResultsModel[TRow, TKey](
    inputModel : Iterable[Future[RepositoryOperationResultModel[TRow, TKey]]]) :
  RepositoryOperationResultsModel[TRow, TKey]
}
