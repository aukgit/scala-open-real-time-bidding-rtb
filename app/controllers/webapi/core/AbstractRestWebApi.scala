package controllers.webapi.core

import controllers.webapi.core.traits.RestWebApiContracts
import play.api.Logger
import play.api.mvc.{ Action, _ }
import shared.com.ortb.constants.AppConstants
import shared.com.ortb.enumeration.DatabaseActionType.DatabaseActionType
import shared.com.ortb.enumeration.HttpActionWrapperType.HttpActionWrapperType
import shared.com.ortb.enumeration.{ DatabaseActionType, HttpActionWrapperType, _ }
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

  val noContentMessage : String = AppConstants.NoContentInRequest

  protected val logger : Logger = Logger(this.getClass)

  override def getAll : Action[AnyContent] = Action {
    implicit request : Request[AnyContent] =>
      val paginationWrapperModel =
        PaginationHelper.getPaginationWrapperModel(request)
      var allEntities : Seq[TRow] = null

      if (paginationWrapperModel.isEmpty) {
        allEntities = service.getAll
      } else {
        // pagination
        val pagedWrapper = paginationWrapperModel.get
        allEntities = service.repository.getCurrentTablePaged(pagedWrapper)
      }

      if (!EmptyValidateHelper.isItemsEmpty(Some(allEntities))) {
        val paginationRequest =
          requests.PaginationRequestModel(
            this,
            request,
            allEntities,
            paginationWrapperModel)
        val response = PaginationHelper.getPaginationResponse(paginationRequest)
        Ok(response)
      } else {
        performBadRequest()
      }
  }

  def getRequestUri(request : Request[AnyContent]) : String = {
    // TODO should return current /suffix url after the domain.
    "/Something after domain TODO"
  }

  def update(id : TKey) : Action[AnyContent] = Action { implicit request =>
    try {
      val webApiEntityResponseWrapper
      : Option[WebApiEntityResponseWrapper[TRow, TKey]] =
        bodyRequestToEntity(request)
      val bodyText = request.body.asText.get
      val optionalEntity =
        webApiEntityResponseWrapper.get.entityWrapper.get.entity
      val entity = optionalEntity.get

      AppLogger.debug("PUT - UpdateRequest")

      AppLogger.logNonFutureNullable(s"Request : $bodyText", optionalEntity)

      if (webApiEntityResponseWrapper == null) {
        BadRequest(
          s"Entity conversion failed for given (source received:$bodyText).")
      }

      val response = service.update(id, entity)
      val attributes = response.attributes.get

      if (attributes.isSuccess) {
        val httpResponseCreateRequestModel = HttpSuccessResponseCreateRequestModel(
          this,
          getRequestUri(request),
          httpActionWrapperType = HttpActionWrapperType.PutUpdateOk,
          databaseActionType = DatabaseActionType.Update,
          repositoryOperationResultModel = Some(response)
        )

        val finalJsonResponse = ResponseHelper.genericControllerResponse
                                              .getControllerSuccessResponse(httpResponseCreateRequestModel)

        Ok(finalJsonResponse)
      } else {
        throw new Exception("Invalid Result")
      }
    } catch {
      case e : Exception =>
        val httpFailedExceptionActionWrapper =
          HttpFailedExceptionActionWrapper[TRow, TKey](
            exception = Some(e),
            resultType = Some(HttpActionWrapperType.PutUpdateFailed),
            rawBodyRequest = request.body.asText,
            databaseActionType = Some(DatabaseActionType.Create)
          )

        AppLogger.error(e)
        performBadRequestOnException(httpFailedExceptionActionWrapper)
        throw e
    }
  }

  def delete(id : TKey) : Action[AnyContent] = Action { implicit request =>
    val response = service.delete(id)
    //noinspection DuplicatedCode
    val attributes = response.attributes.get

    if (attributes.isSuccess) {
      val httpResponseCreateRequestModel = HttpSuccessResponseCreateRequestModel(
        this,
        getRequestUri(request),
        httpActionWrapperType = HttpActionWrapperType.DeleteOk,
        databaseActionType = DatabaseActionType.Delete,
        repositoryOperationResultModel = Some(response)
      )

      val finalJsonResponse = ResponseHelper.genericControllerResponse
                                            .getControllerSuccessResponse(httpResponseCreateRequestModel)

      Ok(finalJsonResponse)
    } else {
      throw new Exception("Invalid Result")
    }
  }

  def byId(id : TKey) : Action[AnyContent] = Action { implicit request =>
    val entity = service.getById(id)
    val json = service.fromEntityToJson(entity)

    if (json.isDefined) {
      Ok(json.get)
    } else {
      performBadRequest()
    }
  }

  /**
   * Http Post method executes this.
   *
   * @return
   */
  def add() : Action[AnyContent] = Action { implicit request =>
    val successHttpActionWrapperType = HttpActionWrapperType.PostAddOk
    val failedHttpActionWrapperType = HttpActionWrapperType.PostAddFailed
    val databaseActionType = DatabaseActionType.Create

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
            httpActionWrapperType = successHttpActionWrapperType,
            databaseActionType = DatabaseActionType.Delete,
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
          getDefaultFailedMessage(databaseActionType = Some(DatabaseActionType.Create))

        val httpFailedActionWrapper = HttpFailedActionWrapper[TRow, TKey](
          additionalMessage = Some(operationFailedMessage),
          resultType = Some(HttpActionWrapperType.PostAddFailed),
          rawBodyRequest = request.body.asText,
          databaseActionType = Some(DatabaseActionType.Create)
        )

        performBadRequest(Some(httpFailedActionWrapper))
      }
    } catch {
      case e : Exception =>
        handleError(request, e)
    }
  }


  private def handleError(
    request : Request[AnyContent],
    exception : Exception,
    databaseActionType: DatabaseActionType,
    httpActionWrapperType : HttpActionWrapperType = HttpActionWrapperType.GenericFailed,
    ) = {
    AppLogger.error(exception)
    val httpFailedExceptionActionWrapper =
      HttpFailedExceptionActionWrapper[TRow, TKey](
        exception = Some(exception),
        resultType = Some(httpActionWrapperType),
        rawBodyRequest = request.body.asText,
        databaseActionType = Some(databaseActionType)
      )

    performBadRequestOnException(httpFailedExceptionActionWrapper)
    throw exception
  }

  override def addEntities() : Action[AnyContent] = Action { implicit request =>
    val entityResponseWrapper = bodyRequestToEntities(request)

    try {
      if (entityResponseWrapper.isDefined) {
        val entities = entityResponseWrapper.get.entityWrapper.get
        val response = service.addEntities(entities.map(w => w.entity.get))
        //noinspection DuplicatedCode
        val httpResponseCreateRequestModel = HttpSuccessResponseCreateRequestModel(
          this,
          getRequestUri(request),
          httpActionWrapperType = HttpActionWrapperType.DeleteOk,
          databaseActionType = DatabaseActionType.Delete,
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
    val entityResponseWrapper = bodyRequestToEntity(request)

    try {
      if (entityResponseWrapper.isDefined) {
        val entity = entityResponseWrapper.get.entityWrapper.get.entity.get
        val r = service.addEntities(entity, addTimes)
        Ok("Ok")
      } else {
        performBadRequest()
      }
    } catch {
      case e : Exception => AppLogger.error(e)
        performBadRequest()
    }
  }


  override def addOrUpdate(id : TKey) : Action[AnyContent] = Action { implicit request =>
    val entityResponseWrapper = bodyRequestToEntity(request)

    try {
      if (entityResponseWrapper.isDefined) {
        val entity = entityResponseWrapper.get.entityWrapper.get.entity.get
        service.addOrUpdate(id, entity = entity)
        Ok(entity.toString)
      } else {
        performBadRequest()
      }
    } catch {
      case e : Exception => AppLogger.error(e)
        performBadRequest()
    }
  }

  override def getDefaultFailedMessage(
    databaseActionType : Option[DatabaseActionType],
    entity : Option[TRow],
    additionalMessage : String) : String = "Failed Operation" // TODO Enhance

  override def getDefaultSuccessMessage(
    databaseActionType : Option[DatabaseActionType],
    entity : Option[TRow],
    additionalMessage  : String) : String = "Success Operation" // TODO Enhance

  override def performBadRequest(
    httpFailedActionWrapper : Option[HttpFailedActionWrapper[TRow, TKey]]) : Result = {
    if (httpFailedActionWrapper.isDefined) {
      return BadRequest(httpFailedActionWrapper.toString)
    }

    BadRequest(getDefaultFailedMessage())
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
