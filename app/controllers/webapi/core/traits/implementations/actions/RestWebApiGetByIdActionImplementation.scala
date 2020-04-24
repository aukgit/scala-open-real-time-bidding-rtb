package controllers.webapi.core.traits.implementations.actions

import controllers.webapi.core.AbstractRestWebApi
import controllers.webapi.core.traits.actions.RestWebApiGetByIdAction
import play.api.mvc.{ Action, _ }
import shared.com.ortb.enumeration.ControllerDefaultActionType
import shared.com.ortb.model.requests.HttpSuccessResponseCreateRequestModel
import shared.com.ortb.model.wrappers.http.ControllerGenericActionWrapper
import shared.io.helpers.ResponseHelper

trait RestWebApiGetByIdActionImplementation[TTable, TRow, TKey]
  extends RestWebApiGetByIdAction[TTable, TRow, TKey] {
  this : AbstractRestWebApi[TTable, TRow, TKey] =>

  //noinspection DuplicatedCode
  def byId(id : TKey) : Action[AnyContent] = Action { implicit request =>
    val actionWrapper = ControllerGenericActionWrapper(
      ControllerDefaultActionType.GetOrRead,
      Some(request))
    val dbAction = actionWrapper.getControllerActionPropertiesWrapper.databaseActionType

    val entity = service.getById(id)

    if (entity.isDefined) {
      val response = service.repository.getRowToResponse(entity, Some(dbAction))
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
      performBadResponse()
    }
  }
}
