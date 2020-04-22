package controllers.webapi.core

import io.circe.generic.codec.DerivedAsObjectCodec
import io.circe.generic.decoding.DerivedDecoder
import io.circe.generic.encoding.DerivedAsObjectEncoder
import play.api.mvc.{Action, _}
import services.core.AbstractBasicPersistentService
import shapeless.Lazy
import shared.com.ortb.enumeration._
import shared.com.ortb.model.wrappers.http._
import shared.com.ortb.model.wrappers.persistent.EntityWrapperWithOptions
import shared.io.helpers.JsonHelper
import shared.io.loggers.AppLogger
import shared.io.traits.jsonParse.JsonCirceDefaultEncoders

abstract class AbstractRestWebApi[TTable, TRow, TKey]
(components : ControllerComponents)
  extends
    AbstractController(components) with
    RestWebApiContracts[TTable, TRow, TKey] {


  val noContentMessage = "No content in request."
  def encoders: JsonCirceDefaultEncoders[TRow]

  def getAll : Action[AnyContent] = Action { implicit request =>
    //      implicit val listEncoder: Encoder[List[CampaignRow]] = deriveEncoder[List[CampaignRow]]
    // implicit val listEncoder: Codec[List[TRow]] =  Codec.derived[List[TRow]]
    val campaigns = service.getAll
    val json = JsonHelper.toJson(campaigns)(encoders.defaultListEncoder)
    Ok(json.get.spaces2)
  }

  //  def byId(id : TKey) : Action[AnyContent] = Action { implicit request =>
  //    val entity = service.getById(id)
  //    val json = entity.asJson.spaces2 //fromEntityToJson(entity)(encoder) // entity.get.asJson.spaces2
  //    Ok(json)
  //  }

  /**
   * Http Post method executes this.
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
