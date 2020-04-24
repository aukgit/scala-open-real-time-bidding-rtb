package controllers.webapi.core.traits.implementations.actions

import controllers.webapi.core.AbstractRestWebApi
import controllers.webapi.core.traits.actions.RestWebApiAddEntitiesAction
import play.api.mvc.{ Action, _ }
import shared.com.ortb.enumeration.ControllerDefaultActionType
import shared.com.ortb.model.requests.HttpSuccessResponseCreateRequestModel
import shared.com.ortb.model.wrappers.http.ControllerGenericActionWrapper
import shared.io.helpers.ResponseHelper
import shared.io.loggers.AppLogger

trait RestWebApiAddEntitiesActionImplementation[TTable, TRow, TKey]
  extends RestWebApiAddEntitiesAction[TTable, TRow, TKey] {
  this : AbstractRestWebApi[TTable, TRow, TKey] =>

  //noinspection DuplicatedCode
  def addEntitiesBySinge(addTimes : Int) : Action[AnyContent] = Action { implicit request =>
    val addActionWrapper = ControllerGenericActionWrapper(
      ControllerDefaultActionType.Add,
      requestContent = Some(request),
      isMultipleTransaction = true)

    val entityResponseWrapper = bodyRequestToEntity(request)

    try {
      if (entityResponseWrapper.isDefined) {
        val entity = entityResponseWrapper.get.entityWrapper.get.entity.get
        val response = service.addEntities(entity, addTimes)
        //noinspection DuplicatedCode
        val httpResponseCreateRequestModel = HttpSuccessResponseCreateRequestModel(
          this,
          getRequestUri(request),
          controllerGenericActionWrapper = addActionWrapper,
          repositoryOperationResultsModel = Some(response)
        )

        val finalJsonResponse = ResponseHelper.genericControllerResponse
                                              .getControllerSuccessResponse(httpResponseCreateRequestModel)

        OkJson(finalJsonResponse)
      } else {
        performBadResponse()
      }
    } catch {
      case e : Exception => handleError(e, addActionWrapper)
    }
  }

  def addEntities() : Action[AnyContent] = Action { implicit request =>
    val addActionWrapper = ControllerGenericActionWrapper(
      ControllerDefaultActionType.AddMany,
      requestContent = Some(request),
      isMultipleTransaction = true)

    val entityResponseWrapper = bodyRequestToEntities(request)

    try {
      if (entityResponseWrapper.isDefined) {
        val entities = entityResponseWrapper.get.entityWrapper.get
        val response = service.addEntities(entities.map(w => w.entity.get))
        //noinspection DuplicatedCode
        val httpResponseCreateRequestModel = HttpSuccessResponseCreateRequestModel(
          this,
          getRequestUri(request),
          controllerGenericActionWrapper = addActionWrapper,
          repositoryOperationResultsModel = Some(response)
        )

        val finalJsonResponse = ResponseHelper.genericControllerResponse
                                              .getControllerSuccessResponse(httpResponseCreateRequestModel)

        OkJson(finalJsonResponse)
      } else {
        performBadResponse()
      }
    } catch {
      case e : Exception => AppLogger.error(e)
        performBadResponse()
    }
  }
}
