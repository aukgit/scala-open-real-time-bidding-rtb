package controllers.webapi.core.traits

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
