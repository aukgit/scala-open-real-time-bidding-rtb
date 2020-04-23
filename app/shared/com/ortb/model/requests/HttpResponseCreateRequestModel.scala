package shared.com.ortb.model.requests

import controllers.webapi.core.AbstractRestWebApi
import shared.com.ortb.enumeration.DatabaseActionType.DatabaseActionType
import shared.com.ortb.enumeration.HttpActionWrapperType.HttpActionWrapperType
import shared.com.ortb.model.results.{ RepositoryOperationResultModel, RepositoryOperationResultsModel }
import shared.com.ortb.model.wrappers.persistent.WebApiEntityResponseWrapper

case class HttpResponseCreateRequestModel[TTable, TRow, TKey](
  controller           : AbstractRestWebApi[TTable, TRow, TKey],
  requestUri           : String = "",
  httpActionWrapperType : HttpActionWrapperType,
  databaseActionType   : DatabaseActionType,
  webApiEntityResponseWrapper : Option[WebApiEntityResponseWrapper[TRow, TKey]] = None,
  repositoryOperationResultModel : Option[RepositoryOperationResultModel[TRow, TKey]] = None,
  repositoryOperationResultsModel : Option[RepositoryOperationResultsModel[TRow, TKey]] = None
)
