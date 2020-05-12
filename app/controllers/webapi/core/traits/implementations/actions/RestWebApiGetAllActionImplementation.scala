package controllers.webapi.core.traits.implementations.actions

import controllers.webapi.core.AbstractRestWebApi
import controllers.webapi.core.traits.actions.RestWebApiGetAllAction
import play.api.mvc.{ Action, _ }
import shared.com.ortb.enumeration.ControllerDefaultActionType
import shared.com.ortb.model.requests.{ HttpSuccessResponseCreateRequestModel, PaginationRequestModel }
import shared.com.ortb.model.wrappers.http.ControllerGenericActionWrapper
import shared.io.helpers.{ EmptyValidateHelper, PaginationHelper, ResponseHelper }

trait RestWebApiGetAllActionImplementation[TTable, TRow, TKey]
  extends RestWebApiGetAllAction[TTable, TRow, TKey] {
  this : AbstractRestWebApi[TTable, TRow, TKey] =>

  //noinspection DuplicatedCode
  override def getAll : Action[AnyContent] = Action { implicit request : Request[AnyContent] =>
    val actionWrapper = ControllerGenericActionWrapper(
      ControllerDefaultActionType.GetOrRead,
      Some(request))

    val dbAction = actionWrapper.getControllerActionPropertiesWrapper.databaseActionType

    val paginationWrapperModel =
      PaginationHelper.getPaginationWrapperModel(request)
    var allEntities : Seq[TRow] = null
    val hasPagination = paginationWrapperModel.isDefined
    if (!hasPagination) {
      allEntities = service.getAll
    } else {
      val pagedWrapper = paginationWrapperModel.get
      allEntities = service.repository.getCurrentTablePaged(pagedWrapper)
    }

    val rowsOption = Some(allEntities)
    val hasResults = EmptyValidateHelper.hasAnyItem(rowsOption)

    if (hasResults && hasPagination) {
      val paginationRequest =
        PaginationRequestModel(
          this,
          request,
          allEntities,
          paginationWrapperModel)

      val response = PaginationHelper.getPaginationResponse(paginationRequest)
      OkJson(response)
    } else if (hasResults) {
      val resultsToResponse = service.repository.getRowsToResponse(rowsOption, Some(dbAction))

      //noinspection DuplicatedCode
      val httpResponseCreateRequestModel = HttpSuccessResponseCreateRequestModel(
        this,
        getRequestUri(request),
        controllerGenericActionWrapper = actionWrapper,
        repositoryOperationResultsModel = Some(resultsToResponse)
      )

      val finalJsonResponse = ResponseHelper.genericControllerResponse
                                            .getControllerSuccessResponse(httpResponseCreateRequestModel)

      OkJson(finalJsonResponse)
    } else {
      performBadResponse()
    }
  }
}
