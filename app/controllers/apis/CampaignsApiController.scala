package controllers.apis

import controllers.webapi.core.AbstractRestWebApi
import io.circe.generic.auto._
import io.circe.parser._
import io.circe.syntax._
import io.circe.{Decoder, Encoder}
import javax.inject.Inject
import play.api.mvc.{Action, Request, _}
import services.CampaignService
import services.core.AbstractBasicPersistentService
import shared.com.ortb.enumeration.DatabaseActionType.DatabaseActionType
import shared.com.ortb.model.wrappers.http._
import shared.com.ortb.model.wrappers.persistent.{WebApiEntitiesResponseWrapper, _}
import shared.com.ortb.persistent.schema
import shared.com.ortb.persistent.schema.Tables
import shared.com.ortb.persistent.schema.Tables._
import shared.io.loggers.AppLogger

class CampaignsApiController @Inject()(
  campaignService : CampaignService,
  components      : ControllerComponents)
  extends AbstractRestWebApi[Campaign, CampaignRow, Int](components) {

  //  override def getAll : Action[AnyContent] = Action { implicit request : Request[AnyContent] =>
  //    val campaigns = campaignService.getAll
  //    val json = campaigns.asJson.spaces2
  //    Ok(json)
  //  }
  //
  //  override def byId(id : Int) : Action[AnyContent] = Action { implicit request =>
  //    val campaign = campaignService.getById(id)
  //    val json = campaign.get.asJson.spaces2
  //    Ok(json)
  //  }
  //
  //  override def add() : Action[AnyContent] = Action { implicit request =>
  //    try {
  //      val body = request.body.asText.getOrElse("")
  //      val entity = decode[CampaignRow](body).getOrElse(null)
  //      val response : RepositoryOperationResult[CampaignRow, Int] = campaignService.add(entity)
  //
  //      if (response.isSuccess) {
  //        val json = response.entity.get.asJson.spaces2
  //        Ok(json)
  //      }
  //      else {
  //        BadRequest(s"Failed to create. (raw body: $body)")
  //      }
  //    } catch {
  //      case e : Exception => AppLogger.error(e)
  //        BadRequest(e.toString)
  //    }
  //  }

  override def update(id : Int) : Action[AnyContent] = Action {
    implicit request =>
      try {
        val body = request.body.asText.getOrElse("")
        val entity = decode[CampaignRow](body).getOrElse(null)
        AppLogger.debug("put - update")
        AppLogger.logEntityNonFuture(
          isExecute = true,
          entity = Some(entity),
          additionalMessage = body)

        if (entity == null) {
          BadRequest(
            s"Entity conversion failed for given (source received:$body).")
        }

        val response = campaignService.update(id, entity)

        if (!response.isSuccess) {
          BadRequest(s"Update request failed (source received:$body).")
        } else {
          val e2 = response.entity.get
          val json = e2.asJson.spaces2

          AppLogger.logEntityNonFuture(
            isExecute = true,
            entity = Some(e2),
            additionalMessage = json)

          Ok(json)
        }

      } catch {
        case e : Exception =>
          AppLogger.error(e)
          BadRequest(e.toString)
      }
  }

  override def delete(id : Int) : Action[AnyContent] = Action {
    implicit request =>
      val response = campaignService.delete(id)
      val json = response.entity.get.asJson.spaces2
      Ok(json)
  }

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

  override def addEntities() : Action[AnyContent] = Action { implicit  request =>
    val entityResponseWrapper = bodyRequestToEntities(request)

    try{
      if(entityResponseWrapper.isDefined){
        val entities = entityResponseWrapper.get.entityWrapper.get
        val r = service.addEntities(entities.map(w => w.entity.get))
        Ok("Ok")
      } else {
        performBadRequest()
      }
    } catch {
      case e: Exception => AppLogger.error(e)
        performBadRequest()
    }
  }

  def addEntitiesBySinge(addTimes: Int) : Action[AnyContent] = Action { implicit  request =>
    val entityResponseWrapper = bodyRequestToEntity(request)

    try{
      if(entityResponseWrapper.isDefined){
        val entity = entityResponseWrapper.get.entityWrapper.get.entity.get
        val r = service.addEntities(entity, addTimes)
        Ok("Ok")
      } else {
        performBadRequest()
      }
    } catch {
      case e: Exception => AppLogger.error(e)
        performBadRequest()
    }
  }


  override def addOrUpdate(id : Int) : Action[AnyContent] = Action { implicit  request =>
    val entityResponseWrapper = bodyRequestToEntity(request)

    try{
      if(entityResponseWrapper.isDefined){
        val entity = entityResponseWrapper.get.entityWrapper.get.entity.get
        service.addOrUpdate(id, entity= entity)
        Ok(entity.toString)
      } else {
        performBadRequest()
      }
    } catch {
      case e: Exception => AppLogger.error(e)
        performBadRequest()
    }
  }


  override def fromEntityToJson(
    entity : Option[CampaignRow])(
    implicit
    encoder : Encoder[CampaignRow]) : Option[String] =
    service.fromEntityToJson(entity)(encoder)

  override def fromEntitiesToJson(
    entities : Option[List[CampaignRow]])(
    implicit
    encoder : Encoder[List[CampaignRow]]) : Option[String] =
    service.fromEntitiesToJson(entities)(encoder)

  override def fromJsonToEntityWrapper(jsonContent : Option[String])(
    implicit
    decoder : Decoder[CampaignRow]) : Option[EntityWrapperWithOptions[CampaignRow, Int]]
  = service.fromJsonToEntityWrapper(jsonContent)(decoder)

  override def fromJsonToEntitiesWrapper(jsonContent : Option[String])(
    implicit
    decoder : Decoder[List[CampaignRow]]) :
  Option[List[EntityWrapperWithOptions[CampaignRow, Int]]]
  = service.fromJsonToEntitiesWrapper(jsonContent)(decoder)

  override def failedMessage(
    databaseActionType : Option[DatabaseActionType],
    entity : Option[CampaignRow],
    additionalMessage : String) : String = "Failed"

  override def successMessage(
    databaseActionType : Option[DatabaseActionType],
    entity : Option[CampaignRow],
    additionalMessage : String) : String = "Success"

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
    val entityWrapper = fromJsonToEntity(request.body.asText)
    val webApiEntityResponseWrapper = new WebApiEntityResponseWrapper(
      entityWrapper = entityWrapper,
      toString(request))

    Some(webApiEntityResponseWrapper)
  }

  override def bodyRequestToEntities(request : Request[AnyContent])
  : Option[WebApiEntitiesResponseWrapper[CampaignRow, Int]] = {
    val entitiesWrapper = fromJsonToEntities(request.body.asText)
    val webApiEntitiesResponseWrapper =
      WebApiEntitiesResponseWrapper(entitiesWrapper, toString(request))

    Some(webApiEntitiesResponseWrapper)
  }

  override def fromRequestToEntity(request : Option[Request[AnyContent]])
  : Option[EntityWrapperWithOptions[CampaignRow, Int]] = {
    if (request == null || request.isEmpty) {
      AppLogger.debug(noContentMessage)
      return None
    }

    val entityWrapper = fromJsonToEntity(Some(toString(request.get)))

    entityWrapper
  }

  override def fromRequestToEntities(request : Option[Request[AnyContent]])
  : Option[Iterable[EntityWrapperWithOptions[CampaignRow, Int]]] = {
    if (request == null || request.isEmpty) {
      AppLogger.debug(noContentMessage)
      return None
    }

    val entitiesWrapper = fromJsonToEntities(Some(toString(request.get)))

    entitiesWrapper
  }

  override def fromJsonToEntity(jsonString : Option[String])
  : Option[EntityWrapperWithOptions[CampaignRow, Int]] = {
    service.fromJsonToEntityWrapper(jsonString)
  }

  override def fromJsonToEntities(jsonString : Option[String])
  : Option[Iterable[EntityWrapperWithOptions[CampaignRow, Int]]] =
    service.fromJsonToEntitiesWrapper(jsonString)
}
