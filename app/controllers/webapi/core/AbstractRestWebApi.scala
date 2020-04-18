package controllers.webapi.core

import io.circe._
import io.circe.generic.auto._
import play.api.mvc.{Action, _}
import services.core.AbstractBasicPersistentService
import shared.com.ortb.enumeration._
import shared.com.ortb.model.wrappers.http._
import shared.com.ortb.model.wrappers.persistent.EntityWrapperWithOptions
import shared.io.loggers.AppLogger

abstract class AbstractRestWebApi[TTable, TRow, TKey]
(components : ControllerComponents)
  extends
    AbstractController(components) with
    RestWebApiContracts[TTable, TRow, TKey] {

  val service : AbstractBasicPersistentService[TTable, TRow, TKey]
  val noContentMessage = "No content in request."

  lazy implicit val listEncoder : Encoder[List[TRow]] = Encoder[List[TRow]]
  lazy implicit val encoder : Encoder[TRow] = Encoder[TRow]
  lazy implicit val decoder : Decoder[TRow] = Decoder[TRow]
  lazy implicit val listDecoder : Decoder[List[TRow]] = Decoder[List[TRow]]

  def getAll : Action[AnyContent] = Action { implicit request =>
    val campaigns = service.getAll
    val json = fromEntitiesToJson(Some(campaigns))(listEncoder)
    Ok(json.get)
  }

  def byId(id : TKey) : Action[AnyContent] = Action { implicit request =>
    val entity = service.getById(id)
    val json = fromEntityToJson(entity)(encoder) // entity.get.asJson.spaces2
    Ok(json.get)
  }

  def add() : Action[AnyContent] = Action { implicit request =>
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
        val responseEntity = response.entity

        if (response.isSuccess && responseEntity.isDefined) {
          val entityWrapper = Some(EntityWrapperWithOptions[TRow, TKey](
            None,
            responseEntity))

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
