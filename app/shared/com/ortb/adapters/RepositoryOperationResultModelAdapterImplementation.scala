package shared.com.ortb.adapters

import shared.com.ortb.enumeration.DatabaseActionType.DatabaseActionType
import shared.com.ortb.model.repository.GenericResponseAttributesModel
import shared.com.ortb.model.repository.response.{RepositoryOperationResultModel, RepositoryOperationResultsModel}
import shared.io.helpers.BasicAdapterHelper
import shared.io.traits.FutureToRegular

import scala.concurrent.Future

trait RepositoryOperationResultModelAdapterImplementation {
  def getEmptyResponse[TRow, TKey](
    databaseActionType : DatabaseActionType) = new RepositoryOperationResultModel[TRow, TKey](
    attributes = GenericResponseAttributesModel(
      isSuccess = false,
      databaseActionType,
      "Empty response."
    )
  )

  def getEmptyResultsResponse[TRow, TKey](
    databaseActionType : DatabaseActionType) = new
      RepositoryOperationResultsModel[TRow, TKey](
        attributes = GenericResponseAttributesModel(
          isSuccess = false,
          databaseActionType,
          "Empty response."
        ),
        data = null
      )

  def fromRepositoryOperationResultModelsToRepositoryOperationResultsModel[TRow, TKey](
    inputModel : Iterable[Future[RepositoryOperationResultModel[TRow, TKey]]],
    databaseActionType : DatabaseActionType,
    message            : String = "") :
  RepositoryOperationResultsModel[TRow, TKey] = {
    if (inputModel.isEmpty) {
      return getEmptyResultsResponse[TRow, TKey](databaseActionType)
    }

    val items = inputModel.map(w => {
      val response : RepositoryOperationResultModel[TRow, TKey] = FutureToRegular.toRegular(w)
      BasicAdapterHelper.fromResultModelToEntityWrap(response)
    }).toList

    val attributesModel = GenericResponseAttributesModel(
      items.nonEmpty,
      databaseActionType,
      message)

    RepositoryOperationResultsModel(
      attributesModel,
      items
    )
  }
}
