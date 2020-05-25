package shared.com.ortb.webapi.core.traits.implementations.actions

import shared.com.ortb.webapi.core.AbstractRestWebApi
import shared.com.ortb.webapi.core.traits.actions.RestWebApiDeleteAction
import play.api.mvc.{ Action, AnyContent }
import shared.com.ortb.enumeration.ControllerDefaultActionType
import shared.com.ortb.model.requests.HttpSuccessResponseCreateRequestModel
import shared.com.ortb.model.wrappers.http.ControllerGenericActionWrapper
import shared.io.helpers.ResponseHelper

trait RestWebApiDeleteActionImplementation[TTable, TRow, TKey]
  extends RestWebApiDeleteAction[TTable, TRow, TKey] {
  this : AbstractRestWebApi[TTable, TRow, TKey] =>

  //noinspection DuplicatedCode
  def delete(id : TKey) : Action[AnyContent] = Action { implicit request =>
    val actionWrapper = ControllerGenericActionWrapper(
      ControllerDefaultActionType.Delete,
      Some(request))

    try {
      val response = service.delete(id)
      val attributes = response.attributes.get
      if (attributes.isSuccess) {
        //noinspection DuplicatedCode
        val httpResponseCreateRequestModel = HttpSuccessResponseCreateRequestModel(
          this,
          getRequestUri(request),
          controllerGenericActionWrapper = actionWrapper,
          repositoryOperationResultModel = Some(response)
        )

        val finalJsonResponse = ResponseHelper.genericControllerResponse
                                              .getControllerSuccessResponse(httpResponseCreateRequestModel)

        OkJson(finalJsonResponse)
      }
      else {
        throw new Exception("Invalid delete request or failed during performing database transaction.")
      }
    } catch {
      case e : Exception => handleError(e, actionWrapper)
    }
  }
}
