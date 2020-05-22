package shared.com.ortb.webapi.core.traits.implementations.actions

import shared.com.ortb.webapi.core.AbstractRestWebApi
import shared.com.ortb.webapi.core.traits.actions.RestWebApiAddAction
import play.api.mvc.{ Action, _ }
import shared.com.ortb.enumeration.ControllerDefaultActionType
import shared.com.ortb.model.requests.HttpSuccessResponseCreateRequestModel
import shared.com.ortb.model.wrappers.http.{ ControllerGenericActionWrapper, HttpFailedActionWrapper }
import shared.io.helpers.ResponseHelper

trait RestWebApiAddActionImplementation[TTable, TRow, TKey]
  extends RestWebApiAddAction[TTable, TRow, TKey] {
  this : AbstractRestWebApi[TTable, TRow, TKey] =>

  //noinspection DuplicatedCode
  /**
   * Http Post method executes this.
   *
   * @return
   */
  def add() : Action[AnyContent] = Action { implicit request : Request[AnyContent] =>
    val addActionWrapper = ControllerGenericActionWrapper(
      ControllerDefaultActionType.Add,
      Some(request))

    try {
      val entityResponseWrapper = bodyRequestToEntity(request);
      val isDefinedProperly = entityResponseWrapper.isDefined &&
        entityResponseWrapper.get.entityWrapper.isDefined

      if (isDefinedProperly) {
        // has item
        val entityWrapper = entityResponseWrapper.get.entityWrapper.get
        val entity = entityWrapper
        val response = service.addUsingOption(entity.entity).get
        val responseEntity = response.data.get
        val attributes = response.attributes.get

        if (attributes.isSuccess && responseEntity != null) {
          //noinspection DuplicatedCode
          val httpResponseCreateRequestModel = HttpSuccessResponseCreateRequestModel(
            this,
            getRequestUri(request),
            controllerGenericActionWrapper = addActionWrapper,
            repositoryOperationResultModel = Some(response)
          )

          val finalJsonResponse = ResponseHelper.genericControllerResponse
                                                .getControllerSuccessResponse(httpResponseCreateRequestModel)

          OkJson(finalJsonResponse)
        }
        else {
          performBadResponse()
        }
      }
      else {
        val operationFailedMessage =
          getDefaultFailedMessage()

        val httpFailedActionWrapper = HttpFailedActionWrapper[TRow, TKey](
          additionalMessage = Some(operationFailedMessage),
          controllerGenericActionWrapper = addActionWrapper
        )

        performBadResponse(Some(httpFailedActionWrapper))
      }
    } catch {
      case e : Exception =>
        handleError(e, addActionWrapper)
    }
  }
}
