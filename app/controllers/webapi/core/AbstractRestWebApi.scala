package controllers.webapi.core

import controllers.webapi.core.traits.RestWebApiContracts
import play.api.Logger
import play.api.mvc.{ Action, _ }
import shared.com.ortb.constants.AppConstants
import shared.com.ortb.enumeration._
import shared.com.ortb.model.wrappers.PaginationWrapperModel
import shared.com.ortb.model.wrappers.http._
import shared.com.ortb.model.wrappers.persistent.EntityWrapperWithOptions
import shared.io.helpers.EmptyValidateHelper
import shared.io.loggers.AppLogger


abstract class AbstractRestWebApi[TTable, TRow, TKey]
(components : ControllerComponents)
  extends
    AbstractController(components) with
    RestWebApiContracts[TTable, TRow, TKey] {

  val noContentMessage = "No content in request."

  protected val logger : Logger = Logger(this.getClass)

  def getPaginationWrapperModel(request : Request[AnyContent]) : Option[PaginationWrapperModel] = {
    try {
      val page = request.getQueryString(AppConstants.QueryStringNameConstants.page)
      if (EmptyValidateHelper.isEmpty(page)) {
        return None
      }

      val currentPageSize = request.getQueryString(AppConstants.QueryStringNameConstants.pageSize)
      val pageToInt = page.get.toInt

      if (EmptyValidateHelper.isDefined(currentPageSize)) {
        return Some(PaginationWrapperModel(pageToInt, currentPageSize.get.toInt))
      }

      return Some(PaginationWrapperModel(pageToInt, AppConstants.DefaultPageSize))
    } catch {
      case e : Exception => AppLogger.error(e)
    }

    None
  }

  override def getAll : Action[AnyContent] = Action { implicit request : Request[AnyContent] =>
    val paginationWrapperModel = getPaginationWrapperModel(request)
    var allEntities : Seq[TRow] = null

    if (paginationWrapperModel.isEmpty) {
      allEntities = service.getAll
    } else {
      // pagination
      val pagedWrapper = paginationWrapperModel.get
      allEntities = service.repository.getCurrentTablePaged(pagedWrapper)
    }

    val json = service.fromEntitiesToJson(Some(allEntities))
    if (json.isDefined) {
      Ok(json.get)
    } else {
      performBadRequest()
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
    AppLogger.info("Performing add action from abstraction")
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
          val entityWrapper = Some(EntityWrapperWithOptions[TRow, TKey](
            Some(responseEntity.entityId),
            Some(responseEntity.entity)))

          val httpSuccessWrapper = HttpSuccessActionWrapper[TRow, TKey](
            additionalMessage = Some(successMessageToString),
            resultType = Some(HttpActionWrapperType.PutOk),
            entityWrapper = entityWrapper,
            rawBodyRequest = request.body.asText,
            databaseActionType = Some(DatabaseActionType.Create))

          performOkayOnEntity(Some(httpSuccessWrapper))
        }
      }

      val operationFailedMessage = failedMessage(databaseActionType = Some(DatabaseActionType.Create))

      val httpFailedActionWrapper = HttpFailedActionWrapper[TRow, TKey](
        additionalMessage = Some(operationFailedMessage),
        resultType = Some(HttpActionWrapperType.PutFailed),
        rawBodyRequest = request.body.asText,
        databaseActionType = Some(DatabaseActionType.Create))

      performBadRequest(Some(httpFailedActionWrapper))
    } catch {
      case e : Exception => AppLogger.error(e)
        val httpFailedExceptionActionWrapper = HttpFailedExceptionActionWrapper[TRow, TKey](
          exception = Some(e),
          resultType = Some(HttpActionWrapperType.PutFailed),
          rawBodyRequest = request.body.asText,
          databaseActionType = Some(DatabaseActionType.Create))

        performBadRequestOnException(httpFailedExceptionActionWrapper)
    }
  }
}
