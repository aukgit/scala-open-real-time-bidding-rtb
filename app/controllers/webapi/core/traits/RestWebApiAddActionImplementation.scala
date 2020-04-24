package controllers.webapi.core.traits

trait RestWebApiAddActionImplementation[TTable, TRow, TKey]
  extends RestWebApiAddAction[TTable, TRow, TKey] {
  this: AbstractRestWebApi[TTable, TRow, TKey] =>

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
          performBadRequest()
        }
      }
      else {
        val operationFailedMessage =
          getDefaultFailedMessage()

        val httpFailedActionWrapper = HttpFailedActionWrapper[TRow, TKey](
          additionalMessage = Some(operationFailedMessage),
          controllerGenericActionWrapper = addActionWrapper
        )

        performBadRequest(Some(httpFailedActionWrapper))
      }
    } catch {
      case e : Exception =>
        handleError(e, addActionWrapper)
    }
  }

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
        performBadRequest()
      }
    } catch {
      case e : Exception => handleError(e, addActionWrapper)
    }
  }
}
