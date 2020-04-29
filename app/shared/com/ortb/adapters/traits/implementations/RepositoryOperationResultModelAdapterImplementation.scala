package shared.com.ortb.adapters.traits.implementations

import shared.com.ortb.constants.AppConstants
import shared.com.ortb.enumeration.DatabaseActionType.DatabaseActionType
import shared.com.ortb.model.attributes.GenericResponseAttributesModel
import shared.com.ortb.model.results.{ RepositoryOperationResultModel, RepositoryOperationResultsModel }
import shared.com.repository.traits.FutureToRegular
import shared.io.helpers.AdapterHelper

import scala.concurrent.Future

trait RepositoryOperationResultModelAdapterImplementation {
  def getEmptyResponse[TRow, TKey](
    databaseActionType : DatabaseActionType) = new RepositoryOperationResultModel[TRow, TKey](
    attributes = Some(GenericResponseAttributesModel(
      isSuccess = false,
      id = AppConstants.EmptyStringOption,
      ids = None,
      Some(databaseActionType),
      "Empty response."
    ))
  )

  def getEmptyResultsResponse[TRow, TKey](
    databaseActionType : DatabaseActionType) = new
      RepositoryOperationResultsModel[TRow, TKey](
        attributes = Some(GenericResponseAttributesModel(
          isSuccess = false,
          id = AppConstants.EmptyStringOption,
          ids = None,
          Some(databaseActionType),
          "Empty response."
        )),
        data = null
      )

  def fromRepositoryOperationResultModelsToRepositoryOperationResultsModel[TRow, TKey](
    inputModel : Iterable[Future[RepositoryOperationResultModel[TRow, TKey]]],
    databaseActionType : DatabaseActionType,
    message : String = "") :
  RepositoryOperationResultsModel[TRow, TKey] = {
    if (inputModel.isEmpty) {
      return getEmptyResultsResponse[TRow, TKey](databaseActionType)
    }

    val items = inputModel.map(w => {
      val response : RepositoryOperationResultModel[TRow, TKey] = FutureToRegular.toRegular(w)
      AdapterHelper.entityWrapperAdapter.fromResultModelToEntityWrap(response)
    }).toList

    val ids = items.map(w=> w.entityId.toString)

    val attributesModel = GenericResponseAttributesModel(
      items.nonEmpty,
      id = Some(ids.head),
      ids = Some(ids),
      Some(databaseActionType),
      message)

    RepositoryOperationResultsModel(
      Some(attributesModel),
      items
    )
  }
}
