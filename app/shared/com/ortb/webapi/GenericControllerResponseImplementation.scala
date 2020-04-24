package shared.com.ortb.webapi

import shared.com.ortb.model.attributes.{ GenericControllerResponseAttributesModel, GenericResponseAttributesModel }
import shared.com.ortb.model.requests.HttpSuccessResponseCreateRequestModel
import shared.com.ortb.model.results._
import shared.com.ortb.model.wrappers.persistent.WebApiEntityResponseWrapper
import shared.com.ortb.webapi.traits.GenericControllerResponse
import shared.io.helpers.EmptyValidateHelper

class GenericControllerResponseImplementation extends GenericControllerResponse {
  //TODO : Refactor to SOLID as REPOSITORY
  def getGenericResponseAttributesModelToGenericControllerResponseAttributesModel[TTable, TRow, TKey](
    httpResponseCreateRequestModel : HttpSuccessResponseCreateRequestModel[TTable, TRow, TKey],
    genericResponseAttributesModel : GenericResponseAttributesModel,
    entityIds                      : Iterable[String] = None)
  : GenericControllerResponseAttributesModel = {
    val hasUri = EmptyValidateHelper.isStringDefined(genericResponseAttributesModel.requestUri)
    val requestUri : String = if (hasUri) genericResponseAttributesModel.requestUri else httpResponseCreateRequestModel
      .requestUri
    GenericControllerResponseAttributesModel(
      genericResponseAttributesModel.isSuccess,
      genericResponseAttributesModel.actionType.get.toString,
      requestUri,
      entityIds,
      genericResponseAttributesModel.message)
  }

  def getSuccessResponseForRepositoryOperationResultModel[TTable, TRow, TKey](
    httpResponseCreateRequestModel    : HttpSuccessResponseCreateRequestModel[TTable, TRow, TKey],
    repositoryResponse                : Option[RepositoryOperationResultModel[TRow, TKey]]) : String = {
    val responseEntityWrapper = repositoryResponse.get.data
    val service = httpResponseCreateRequestModel.controller.service

    val entityWrapper = responseEntityWrapper.get
    val attributes = getGenericResponseAttributesModelToGenericControllerResponseAttributesModel(
      httpResponseCreateRequestModel,
      repositoryResponse.get.attributes.get,
      Seq(entityWrapper.entityId.toString))
    val response = ControllerSuccessResultsModel[TRow, TKey](Some(attributes), Seq(entityWrapper.entity))
    val encoder = service.serviceEncoders.controllerSuccessEncoder
    val finalJson = encoder.getJsonGenericParser
                           .toJsonString(Some(response))
    finalJson.get
  }

  def getSuccessResponseForRepositoryOperationResultsModel[TTable, TRow, TKey](
    httpResponseCreateRequestModel                     : HttpSuccessResponseCreateRequestModel[TTable, TRow, TKey],
    repositoryResponse                                 : Option[RepositoryOperationResultsModel[TRow, TKey]])
  : String = {
    val responseEntityWrapper = repositoryResponse.get.data
    val service = httpResponseCreateRequestModel.controller.service
    val listOfEntityWrappers = responseEntityWrapper
    val rows = responseEntityWrapper.map(w => w.entity)
    val idsString = listOfEntityWrappers.map(w => w.entityId.toString)
    val attributes = getGenericResponseAttributesModelToGenericControllerResponseAttributesModel(
      httpResponseCreateRequestModel,
      repositoryResponse.get.attributes.get,
      idsString)
    val response = ControllerSuccessResultsModel[TRow, TKey](Some(attributes), rows)
    val encoder = service.serviceEncoders.controllerSuccessEncoder
    val finalJson = encoder.getJsonGenericParser
                           .toJsonString(Some(response))
    finalJson.get
  }

  def getSuccessResponseForWebApiResponseWrapper[TTable, TRow, TKey](
    httpResponseCreateRequestModel          : HttpSuccessResponseCreateRequestModel[TTable, TRow, TKey],
    webApiResponse                          : Option[WebApiEntityResponseWrapper[TRow, TKey]]) : String = {
    val responseEntityWrapperOptions = webApiResponse.get.entityWrapper
    val entityWrapper = responseEntityWrapperOptions.get
    val service = httpResponseCreateRequestModel.controller.service
    val attributesModel = GenericControllerResponseAttributesModel(
      isSuccess = true,
      databaseActionPerformed = "Unknown",
      requestUri = httpResponseCreateRequestModel.requestUri,
      entityIds = Seq(entityWrapper.entityId.get.toString)
    )

    val response = ControllerSuccessResultsModel[TRow, TKey](Some(attributesModel), Seq(entityWrapper.entity.get))
    val encoder = service.serviceEncoders.controllerSuccessEncoder
    val finalJson = encoder.getJsonGenericParser
                           .toJsonString(Some(response))
    finalJson.get
  }

  def getControllerSuccessResponse[TTable, TRow, TKey]
    (httpResponseCreateRequestModel : HttpSuccessResponseCreateRequestModel[TTable, TRow, TKey]) : String = {
    EmptyValidateHelper.throwOnNullOrNone(httpResponseCreateRequestModel)

    val repositoryResponse = httpResponseCreateRequestModel.repositoryOperationResultModel
    if (EmptyValidateHelper.isDefined(repositoryResponse)) {
      return getSuccessResponseForRepositoryOperationResultModel(
        httpResponseCreateRequestModel : HttpSuccessResponseCreateRequestModel[TTable, TRow, TKey],
        repositoryResponse
      )
    }

    val repositoryResultsResponse = httpResponseCreateRequestModel.repositoryOperationResultsModel
    if (EmptyValidateHelper.isDefined(repositoryResultsResponse)) {
      return getSuccessResponseForRepositoryOperationResultsModel(
        httpResponseCreateRequestModel : HttpSuccessResponseCreateRequestModel[TTable, TRow, TKey],
        repositoryResultsResponse
      )
    }

    val webApiResponse = httpResponseCreateRequestModel.webApiEntityResponseWrapper
    if (EmptyValidateHelper.isDefined(webApiResponse)) {
      return getSuccessResponseForWebApiResponseWrapper(
        httpResponseCreateRequestModel : HttpSuccessResponseCreateRequestModel[TTable, TRow, TKey],
        webApiResponse
      )
    }

    throw new Exception("No valid response, all the responses are empty.")
  }
}
