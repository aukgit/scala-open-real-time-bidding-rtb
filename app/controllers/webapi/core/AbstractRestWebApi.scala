package controllers.webapi.core

import controllers.webapi.core.traits.RestWebApiContracts
import play.api.mvc.{ Action, _ }
import shared.com.ortb.enumeration._
import shared.com.ortb.model.requests
import shared.com.ortb.model.requests.HttpSuccessResponseCreateRequestModel
import shared.com.ortb.model.wrappers.http._
import shared.com.ortb.model.wrappers.persistent.{ WebApiEntitiesResponseWrapper, WebApiEntityResponseWrapper }
import shared.io.helpers._
import shared.io.loggers.AppLogger

abstract class AbstractRestWebApi[TTable, TRow, TKey](
  components : ControllerComponents)
  extends AbstractController(components)
    with RestWebApiContracts[TTable, TRow, TKey] {

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
        requests.PaginationRequestModel(
          this,
          request,
          allEntities,
          paginationWrapperModel)

      val response = PaginationHelper.getPaginationResponse(paginationRequest)
      Ok(response)
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

      Ok(finalJsonResponse)
    } else {
      performBadRequest()
    }
  }

  def getRequestUri(request : Request[AnyContent]) : String = {
    request.uri.toString
  }

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

        Ok(finalJsonResponse)
      } else {
        throw new Exception(s"Invalid result during update. Action: $actionWrapper")
      }
    } catch {
      case e : Exception => handleError(e, actionWrapper)
    }
  }

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

        Ok(finalJsonResponse)
      }
      else {
        throw new Exception("Invalid delete request or failed during performing database transaction.")
      }
    } catch {
      case e : Exception => handleError(e, actionWrapper)
    }
  }

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
      Ok(finalJsonResponse)
    } else {
      performBadRequest()
    }
  }

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

          Ok(finalJsonResponse)
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

  def createHttpFailedActionWrapper(
    message: String,
    actionWrapper: ControllerGenericActionWrapper,
    methodName : String = ""
  ) : HttpFailedActionWrapper[TRow, TKey] = {
    AppLogger.error(s"${message} $methodName")
    val httpFailedActionWrapper = HttpFailedActionWrapper[TRow, TKey](
      additionalMessage = Some(message),
      methodName = Some(methodName),
      controllerGenericActionWrapper = actionWrapper)

    httpFailedActionWrapper
  }

  def handleError(
    exception : Exception,
    controllerGenericActionWrapper : ControllerGenericActionWrapper
  ) : Result = {
    AppLogger.error(exception)
    val httpFailedExceptionActionWrapper =
      HttpFailedExceptionActionWrapper[TRow, TKey](
        exception = Some(exception),
        controllerGenericActionWrapper = controllerGenericActionWrapper
      )

    performBadRequestOnException(httpFailedExceptionActionWrapper)
    throw exception
  }

  override def addEntities() : Action[AnyContent] = Action { implicit request =>
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

        Ok(finalJsonResponse)
      } else {
        performBadRequest()
      }
    } catch {
      case e : Exception => AppLogger.error(e)
        performBadRequest()
    }
  }

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

        Ok(finalJsonResponse)
      } else {
        performBadRequest()
      }
    } catch {
      case e : Exception => handleError(e, addActionWrapper)
    }
  }

  override def addOrUpdate(id : TKey) : Action[AnyContent] = Action { implicit request =>
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

        Ok(finalJsonResponse)
      } else {
        performBadRequest()
      }
    } catch {
      case e : Exception => handleError(e, addOrUpdateActionWrapper)
    }
  }

  override def getDefaultFailedMessage(
    controllerGenericActionWrapper : Option[ControllerGenericActionWrapper] = None,
    entity : Option[TRow] = None,
    message : String = "") : String = "Failed Operation" // TODO Enhance

  override def getDefaultSuccessMessage(
    controllerGenericActionWrapper : Option[ControllerGenericActionWrapper] = None,
    entity : Option[TRow] = None,
    message : String = "") : String = "Success Operation" // TODO Enhance

  override def performBadRequest(
    httpFailedActionWrapper : Option[HttpFailedActionWrapper[TRow, TKey]]) : Result = {
    if (httpFailedActionWrapper.isDefined) {
      return BadRequest(httpFailedActionWrapper.toString)
    }

    BadRequest(getDefaultFailedMessage())
  }

  def performBadRequestAsAction(
    httpFailedActionWrapper : Option[HttpFailedActionWrapper[TRow, TKey]]) : Action[AnyContent] = {
    if (httpFailedActionWrapper.isDefined) {
      components.actionBuilder(BadRequest(httpFailedActionWrapper.toString))
    } else {
      components.actionBuilder(BadRequest(getDefaultFailedMessage()))
    }
  }

  def performBadRequestOnException(
    httpFailedActionWrapper : HttpFailedExceptionActionWrapper[TRow, TKey]) : Result =
    BadRequest(httpFailedActionWrapper.toString)

  def performOkayOnEntity(
    httpSuccessActionWrapper : Option[HttpSuccessActionWrapper[TRow, TKey]]) : Result = {
    if (httpSuccessActionWrapper.isDefined) {
      return Ok(httpSuccessActionWrapper.toString)
    }

    Ok(getDefaultSuccessMessage())
  }

  def performOkay(
    httpSuccessActionWrapper : Option[HttpSuccessActionWrapper[TRow, TKey]]) : Result = {
    if (httpSuccessActionWrapper.isDefined) {
      return Ok(httpSuccessActionWrapper.toString)
    }

    Ok(getDefaultSuccessMessage())
  }

  def bodyToString(request : Request[AnyContent]) : String =
    request.body.asText.getOrElse("")

  def bodyRequestToEntity(request          : Request[AnyContent])
  : Option[WebApiEntityResponseWrapper[TRow, TKey]] = {
    val entityWrapper = service.fromJsonToEntityWrapper(request.body.asText)
    val webApiEntityResponseWrapper = WebApiEntityResponseWrapper(
      entityWrapper = entityWrapper,
      bodyToString(request))

    Some(webApiEntityResponseWrapper)
  }

  def bodyRequestToEntities(request          : Request[AnyContent])
  : Option[WebApiEntitiesResponseWrapper[TRow, TKey]] = {
    val entitiesWrapper = service.fromJsonToEntitiesWrapper(request.body.asText)
    val webApiEntitiesResponseWrapper =
      WebApiEntitiesResponseWrapper(entitiesWrapper, bodyToString(request))

    Some(webApiEntitiesResponseWrapper)
  }
}
