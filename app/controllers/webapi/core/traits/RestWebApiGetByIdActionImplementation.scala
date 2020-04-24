package controllers.webapi.core.traits

trait RestWebApiGetByIdActionImplementation[TTable, TRow, TKey]
  extends RestWebApiGetByIdAction[TTable, TRow, TKey] {
  this: AbstractRestWebApi[TTable, TRow, TKey] =>

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
      performBadRequest()
    }
  }
}
