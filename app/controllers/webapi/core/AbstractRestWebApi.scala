package controllers.webapi.core

import controllers.webapi.core.traits.RestWebApiContracts
import play.api.Logger
import play.api.mvc.{Action, _}
import shared.com.ortb.enumeration._
import shared.com.ortb.model.requests
import shared.com.ortb.model.wrappers.http._
import shared.com.ortb.model.wrappers.persistent.{
  EntityWrapperWithOptions,
  WebApiEntityResponseWrapper
}
import shared.io.helpers._
import shared.io.loggers.AppLogger

abstract class AbstractRestWebApi[TTable, TRow, TKey](
    components: ControllerComponents)
    extends AbstractController(components)
    with RestWebApiContracts[TTable, TRow, TKey] {

  val noContentMessage = "No content in request."

  protected val logger: Logger = Logger(this.getClass)

  override def getAll: Action[AnyContent] = Action {
    implicit request: Request[AnyContent] =>
      val paginationWrapperModel =
        PaginationHelper.getPaginationWrapperModel(request)
      var allEntities: Seq[TRow] = null

      if (paginationWrapperModel.isEmpty) {
        allEntities = service.getAll
      } else {
        // pagination
        val pagedWrapper = paginationWrapperModel.get
        allEntities = service.repository.getCurrentTablePaged(pagedWrapper)
      }

      if (!EmptyValidateHelper.isItemsEmpty(Some(allEntities))) {
        val paginationRequest =
          requests.PaginationRequestModel(this,
                                          request,
                                          allEntities,
                                          paginationWrapperModel)
        val response = PaginationHelper.getPaginationResponse(paginationRequest)
        Ok(response)
      } else {
        performBadRequest()
      }
  }

  def update(id: TKey): Action[AnyContent] = Action { implicit request =>
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

      if (!attributes.isSuccess) {
        val failedMessage =
          s"Update request failed (source received:$bodyText)."
        
        val httpFailedActionWrapper = HttpFailedActionWrapper[TRow, TKey](
          additionalMessage = Some(failedMessage),
          resultType = Some(HttpActionWrapperType.PutUpdateFailed),
          rawBodyRequest = request.body.asText,
          databaseActionType = Some(DatabaseActionType.Update)
        )

        performBadRequest(Some(httpFailedActionWrapper))
      } else {
        val responseEntityWrapper = response.data
        val entityWrapperWithOptions = BasicAdapterHelper.entityWrapperAdapter
          .fromEntityWrapperToEntityWrapperWithOptions[TRow, TKey](
            responseEntityWrapper.get)
        val successMessageToString = successMessage(None)

        val httpSuccessWrapper = HttpSuccessActionWrapper[TRow, TKey](
          additionalMessage = Some(successMessageToString),
          resultType = Some(HttpActionWrapperType.PutOk),
          entityWrapper = Some(entityWrapperWithOptions),
          rawBodyRequest = request.body.asText,
          databaseActionType = Some(DatabaseActionType.Update)
        )

        performOkayOnEntity(Some(httpSuccessWrapper))
      }
    } catch {
      case e: Exception =>
        AppLogger.error(e)
        val httpFailedExceptionActionWrapper =
          HttpFailedExceptionActionWrapper[TRow, TKey](
            exception = Some(e),
            resultType = Some(HttpActionWrapperType.PutUpdateFailed),
            rawBodyRequest = request.body.asText,
            databaseActionType = Some(DatabaseActionType.Create)
          )

        performBadRequestOnException(httpFailedExceptionActionWrapper)
    }
  }

  def delete(id: TKey): Action[AnyContent] = Action { implicit request =>
    val response = service.delete(id)
    val json = response.data.get // TODO convert to JSON
    //noinspection DuplicatedCode
    val successMessageToString = successMessage(None)
    val httpSuccessWrapper = HttpSuccessActionWrapper[TRow, TKey](
      additionalMessage = Some(successMessageToString),
      resultType = Some(HttpActionWrapperType.PutOk),
//      entityWrapper = Some(entityWrapperWithOptions),
      rawBodyRequest = request.body.asText,
      databaseActionType = Some(DatabaseActionType.Update)
    )

    performOkayOnEntity(Some(httpSuccessWrapper))
//    Ok(json)
  }

  def byId(id: TKey): Action[AnyContent] = Action { implicit request =>
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
  def add(): Action[AnyContent] = Action { implicit request =>
    try {
      val entityResponseWrapper = bodyRequestToEntity(request);
      val isDefinedProperly = entityResponseWrapper.isDefined &&
        entityResponseWrapper.get.entityWrapper.isDefined

      if (isDefinedProperly) {
        // has item
        val entityWrapper = entityResponseWrapper.get.entityWrapper.get
        val entity = entityWrapper
        val response = service.addUsingOption(entity.entity).get
        val successMessageToString = successMessage(None)
        val responseEntity = response.data.get
        val attributes = response.attributes.get

        if (attributes.isSuccess && responseEntity != null) {
          val entityWrapper = Some(
            EntityWrapperWithOptions[TRow, TKey](Some(responseEntity.entityId),
                                                 Some(responseEntity.entity)))

          val httpSuccessWrapper = HttpSuccessActionWrapper[TRow, TKey](
            additionalMessage = Some(successMessageToString),
            resultType = Some(HttpActionWrapperType.PutOk),
            entityWrapper = entityWrapper,
            rawBodyRequest = request.body.asText,
            databaseActionType = Some(DatabaseActionType.Create)
          )

          performOkayOnEntity(Some(httpSuccessWrapper))
        }
      }

      val operationFailedMessage =
        failedMessage(databaseActionType = Some(DatabaseActionType.Create))

      val httpFailedActionWrapper = HttpFailedActionWrapper[TRow, TKey](
        additionalMessage = Some(operationFailedMessage),
        resultType = Some(HttpActionWrapperType.PostAddFailed),
        rawBodyRequest = request.body.asText,
        databaseActionType = Some(DatabaseActionType.Create)
      )

      performBadRequest(Some(httpFailedActionWrapper))
    } catch {
      case e: Exception =>
        AppLogger.error(e)
        val httpFailedExceptionActionWrapper =
          HttpFailedExceptionActionWrapper[TRow, TKey](
            exception = Some(e),
            resultType = Some(HttpActionWrapperType.PostAddOk),
            rawBodyRequest = request.body.asText,
            databaseActionType = Some(DatabaseActionType.Create)
          )

        performBadRequestOnException(httpFailedExceptionActionWrapper)
    }
  }
}
