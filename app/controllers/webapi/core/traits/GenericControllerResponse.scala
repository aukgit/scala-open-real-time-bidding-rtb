package controllers.webapi.core.traits

import shared.com.ortb.model.attributes.{ GenericControllerResponseAttributesModel, GenericResponseAttributesModel }
import shared.com.ortb.model.requests.HttpSuccessResponseCreateRequestModel
import shared.com.ortb.model.results.{ RepositoryOperationResultModel, RepositoryOperationResultsModel }
import shared.com.ortb.model.wrappers.persistent.WebApiEntityResponseWrapper

trait GenericControllerResponse {
  def getGenericResponseAttributesModelToGenericControllerResponseAttributesModel[TTable, TRow, TKey](
    httpResponseCreateRequestModel : HttpSuccessResponseCreateRequestModel[TTable, TRow, TKey],
    genericResponseAttributesModel    : GenericResponseAttributesModel,
    entityIds                         : Iterable[String] = None) : GenericControllerResponseAttributesModel

  def getSuccessResponseForRepositoryOperationResultModel[TTable, TRow, TKey](
    httpResponseCreateRequestModel : HttpSuccessResponseCreateRequestModel[TTable, TRow, TKey],
    repositoryResponse             : Option[RepositoryOperationResultModel[TRow, TKey]]) : String

  def getSuccessResponseForRepositoryOperationResultsModel[TTable, TRow, TKey](
    httpResponseCreateRequestModel : HttpSuccessResponseCreateRequestModel[TTable, TRow, TKey],
    repositoryResponse             : Option[RepositoryOperationResultsModel[TRow, TKey]]) : String

  def getSuccessResponseForWebApiResponseWrapper[TTable, TRow, TKey](
    httpResponseCreateRequestModel : HttpSuccessResponseCreateRequestModel[TTable, TRow, TKey],
    webApiResponse                 : Option[WebApiEntityResponseWrapper[TRow, TKey]]) : String

  def getControllerSuccessResponse[TTable, TRow, TKey]
    (httpResponseCreateRequestModel : HttpSuccessResponseCreateRequestModel[TTable, TRow, TKey]) : String
}
