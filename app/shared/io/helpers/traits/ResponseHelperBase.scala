package shared.io.helpers.traits

import shared.com.ortb.model.attributes.{ GenericControllerResponseAttributesModel, GenericResponseAttributesModel }
import shared.com.ortb.model.requests.HttpResponseCreateRequestModel
import shared.com.ortb.model.results.{ ControllerSuccessResultsModel, RepositoryOperationResultModel }
import shared.io.helpers.EmptyValidateHelper

trait ResponseHelperBase {

}

object ResponseHelper extends ResponseHelperBase {

  def getGenericResponseAttributesModelToGenericControllerResponseAttributesModel[TKey](
    genericResponseAttributesModel : GenericResponseAttributesModel,
    entityIds: Iterable[String] = None):GenericControllerResponseAttributesModel = {
    GenericControllerResponseAttributesModel(
      genericResponseAttributesModel.isSuccess,
      genericResponseAttributesModel.actionType.toString,
      entityIds,
      genericResponseAttributesModel.message)
  }

  def getSuccessResponseForRepositoryOperationResultModel[TTable, TRow, TKey](
    httpResponseCreateRequestModel : HttpResponseCreateRequestModel[TTable, TRow, TKey],
    repositoryResponse : Option[RepositoryOperationResultModel[TRow, TKey]]) : String = {
    val responseEntityWrapper = repositoryResponse.get.data
    val service = httpResponseCreateRequestModel.controller.service
    val encoder = service.controllerSuccessEncoder.
    val entityWrapper = responseEntityWrapper.get
    val attributes = getGenericResponseAttributesModelToGenericControllerResponseAttributesModel(
      repositoryResponse.get.attributes.get,
      Seq(entityWrapper.entityId.toString))
    val response  = ControllerSuccessResultsModel[TRow, TKey](Some(attributes), Seq(entityWrapper.entity))

    Some(response)
    ""
  }

  def getSuccessResponse[TTable, TRow, TKey]
    (httpResponseCreateRequestModel : HttpResponseCreateRequestModel[TTable, TRow, TKey]) : String = {
    EmptyValidateHelper.throwOnNullOrNone(httpResponseCreateRequestModel)

    val repositoryResponse = httpResponseCreateRequestModel.repositoryOperationResultModel
    if (EmptyValidateHelper.isDefined(repositoryResponse)) {
      return getSuccessResponseForRepositoryOperationResultModel(
        httpResponseCreateRequestModel : HttpResponseCreateRequestModel[TTable, TRow, TKey],
        repositoryResponse
      )
    }

    //   val responseEntityWrapper = repositoryResponse.entityWrapper
    //   val entityWrapperWithOptions = BasicAdapterHelper.entityWrapperAdapter
    //                                                    .fromEntityWrapperToEntityWrapperWithOptions[TRow, TKey](
    //                                                      responseEntityWrapper.get)
    //   val successMessageToString = successMessage(None)
    //
    //   val httpSuccessWrapper = HttpSuccessActionWrapper[TRow, TKey](
    //     additionalMessage = Some(successMessageToString),
    //     resultType = Some(HttpActionWrapperType.PutOk),
    //     entityWrapper = Some(entityWrapperWithOptions),
    //     rawBodyRequest = request.body.asText,
    //     databaseActionType = Some(DatabaseActionType.Update)
    //   )
    ""
  }
}
