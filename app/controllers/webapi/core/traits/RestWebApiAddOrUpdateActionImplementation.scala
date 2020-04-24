package controllers.webapi.core.traits

trait RestWebApiAddOrUpdateActionImplementation[TTable, TRow, TKey]
  extends RestWebApiAddOrUpdateAction[TTable, TRow, TKey] {
  this: AbstractRestWebApi[TTable, TRow, TKey] =>

  def addOrUpdate(id : TKey) : Action[AnyContent] = Action { implicit request =>
    val addOrUpdateActionWrapper = ControllerGenericActionWrapper(
      ControllerDefaultActionType.AddOrUpdate,
      requestContent = Some(request))

    val entityResponseWrapper = bodyRequestToEntity(request)

    try {
      if (entityResponseWrapper.isDefined) {
        val entity = entityResponseWrapper.get.entityWrapper.get.entity.get
        val response = service.addOrUpdate(id, entity = entity)
        //noinspection DuplicatedCode
        val httpResponseCreateRequestModel = HttpSuccessResponseCreateRequestModel(
          this,
          getRequestUri(request),
          controllerGenericActionWrapper = addOrUpdateActionWrapper,
          repositoryOperationResultModel = Some(response)
        )

        val finalJsonResponse = ResponseHelper.genericControllerResponse
                                              .getControllerSuccessResponse(httpResponseCreateRequestModel)

        OkJson(finalJsonResponse)
      } else {
        performBadRequest()
      }
    } catch {
      case e : Exception => handleError(e, addOrUpdateActionWrapper)
    }
  }
}
