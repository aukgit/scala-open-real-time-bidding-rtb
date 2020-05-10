package shared.com.ortb.model.requests

import controllers.webapi.core.AbstractRestWebApi
import shared.com.ortb.model.results.{ RepositoryOperationResultModel, RepositoryOperationResultsModel }
import shared.com.ortb.model.wrappers.http.ControllerGenericActionWrapper
import shared.com.ortb.model.wrappers.persistent.WebApiEntityResponseWrapper

case class HttpSuccessResponseCreateRequestModel[TTable, TRow, TKey](
  controller : AbstractRestWebApi[TTable, TRow, TKey],
  requestUri : String = "",
  controllerGenericActionWrapper : ControllerGenericActionWrapper,
  webApiEntityResponseWrapper : Option[WebApiEntityResponseWrapper[TRow, TKey]] = None,
  repositoryOperationResultModel : Option[RepositoryOperationResultModel[TRow, TKey]] = None,
  repositoryOperationResultsModel : Option[RepositoryOperationResultsModel[TRow, TKey]] = None
)
