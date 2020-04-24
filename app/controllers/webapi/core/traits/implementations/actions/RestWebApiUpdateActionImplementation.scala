package controllers.webapi.core.traits.implementations.actions

import controllers.webapi.core.AbstractRestWebApi
import controllers.webapi.core.traits.actions.RestWebApiUpdateAction
import play.api.mvc.{ Action, AnyContent }
import shared.com.ortb.enumeration.ControllerDefaultActionType
import shared.com.ortb.model.requests.HttpSuccessResponseCreateRequestModel
import shared.com.ortb.model.wrappers.http.ControllerGenericActionWrapper
import shared.io.helpers.ResponseHelper
import shared.io.loggers.AppLogger

trait RestWebApiUpdateActionImplementation[TTable, TRow, TKey]
  extends RestWebApiUpdateAction[TTable, TRow, TKey] {
  this: AbstractRestWebApi[TTable, TRow, TKey] =>

  //noinspection DuplicatedCode
  def update(id : TKey) : Action[AnyContent] = Action { implicit request =>
    val actionWrapper = ControllerGenericActionWrapper(
      ControllerDefaultActionType.Update,
      Some(request))

    try {
      val webApiEntityResponseWrapper = bodyRequestToEntity(request)
      val bodyText = request.body.asText.get
      val optionalEntity = webApiEntityResponseWrapper.get.entityWrapper.get.entity
      val entity = optionalEntity.get

      AppLogger.logNonFutureNullable(s"Request : $bodyText", optionalEntity)

      if (webApiEntityResponseWrapper == null) {
        BadRequest(
          s"Entity conversion failed for given (source received:$bodyText).")
      }

      val response = service.update(id, entity)
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
      } else {
        throw new Exception(s"Invalid result during update. Action: $actionWrapper")
      }
    } catch {
      case e : Exception => handleError(e, actionWrapper)
    }
  }
}
