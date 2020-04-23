package controllers.apis

import controllers.webapi.core.AbstractRestWebApi
import io.circe.generic.auto._
import io.circe.parser._
import io.circe.syntax._
import io.circe.{ Decoder, Encoder }
import javax.inject.Inject
import play.api.mvc.{ Action, Request, _ }
import services.CampaignService
import services.core.AbstractBasicPersistentService
import shared.com.ortb.enumeration.DatabaseActionType.DatabaseActionType
import shared.com.ortb.model.wrappers.http._
import shared.com.ortb.model.wrappers.persistent.{ WebApiEntitiesResponseWrapper, _ }
import shared.com.ortb.persistent.schema.Tables
import shared.com.ortb.persistent.schema.Tables._
import shared.io.jsonParse.implementations.JsonCirceDefaultEncodersImplementation
import shared.io.loggers.AppLogger

class CampaignsApiController @Inject()(
  campaignService : CampaignService,
  components      : ControllerComponents)
  extends AbstractRestWebApi[Campaign, CampaignRow, Int](components) {



  //
  //  override def addEntities(
  //    entities : Iterable[Tables.CampaignRow]) : Action[AnyContent] = Action { implicit request =>
  //    val response = campaignService.addEntities(entities)
  //                                  .map(w => w.entity).toArray
  //    val json = response.asJson.spaces2
  //    Ok(json)
  //  }
  //
  //  override def addEntitiesBySinge(
  //    entity   : Tables.CampaignRow,
  //    addTimes : Int) : Action[AnyContent] = Action { implicit request =>
  //    val response = campaignService.addEntities(entity, addTimes)
  //                                  .map(w => w.entity).toArray
  //
  //    val json = response.asJson.spaces2
  //    Ok(json)
  //  }
  override val service
  : AbstractBasicPersistentService[Campaign, CampaignRow, Int] =
  campaignService

  override def addEntities() : Action[AnyContent] = Action { implicit request =>
    val entityResponseWrapper = bodyRequestToEntities(request)

    try {
      if (entityResponseWrapper.isDefined) {
        val entities = entityResponseWrapper.get.entityWrapper.get
        val r = service.addEntities(entities.map(w => w.entity.get))
        Ok("Ok")
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


  override def addOrUpdate(id : Int) : Action[AnyContent] = Action { implicit request =>
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

  override def failedMessage(
    databaseActionType : Option[DatabaseActionType],
    entity             : Option[CampaignRow],
    additionalMessage  : String) : String = "Failed"

  override def successMessage(
    databaseActionType : Option[DatabaseActionType],
    entity             : Option[CampaignRow],
    additionalMessage  : String) : String = "Success"

  override def performBadRequest(
    httpFailedActionWrapper : Option[HttpFailedActionWrapper[CampaignRow, Int]]) : Result =
    BadRequest(httpFailedActionWrapper.toString)

  override def performBadRequestOnException(
    httpFailedActionWrapper : HttpFailedExceptionActionWrapper[CampaignRow, Int]) : Result =
    BadRequest(httpFailedActionWrapper.toString)

  override def performOkayOnEntity(
    httpSuccessActionWrapper : Option[HttpSuccessActionWrapper[CampaignRow, Int]]) : Result =
    Ok(httpSuccessActionWrapper.toString)

  override def performOkay(
    httpSuccessActionWrapper : Option[HttpSuccessActionWrapper[CampaignRow, Int]]) : Result =
    Ok(httpSuccessActionWrapper.toString)

  override def toString(request : Request[AnyContent]) : String =
    request.body.asText.getOrElse("")

  override def bodyRequestToEntity(request : Request[AnyContent])
  : Option[WebApiEntityResponseWrapper[Tables.CampaignRow, Int]] = {
    val entityWrapper = service.fromJsonToEntityWrapper(request.body.asText)
    val webApiEntityResponseWrapper = new WebApiEntityResponseWrapper(
      entityWrapper = entityWrapper,
      toString(request))

    Some(webApiEntityResponseWrapper)
  }

  override def bodyRequestToEntities(request : Request[AnyContent])
  : Option[WebApiEntitiesResponseWrapper[CampaignRow, Int]] = {
    val entitiesWrapper = service.fromJsonToEntitiesWrapper(request.body.asText)
    val webApiEntitiesResponseWrapper =
      WebApiEntitiesResponseWrapper(entitiesWrapper, toString(request))

    Some(webApiEntitiesResponseWrapper)
  }
}
