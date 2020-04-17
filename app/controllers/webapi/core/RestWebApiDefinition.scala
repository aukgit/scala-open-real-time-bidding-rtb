package controllers.webapi.core
import io.circe.generic.auto._
import io.circe.parser._
import io.circe.syntax._
import javax.inject.Inject
import play.api.mvc.{Action, Request, _}
import services.CampaignService
import services.traits.ServiceBasicPersistentOperations
import shared.com.ortb.enumeration.DatabaseActionType.DatabaseActionType
import shared.com.ortb.enumeration.HttpMethodType.HttpMethodType
import shared.com.ortb.model.results.RepositoryOperationResult
import shared.com.ortb.model.wrappers.persistent.{WebApiEntitiesResponseWrapper, WebApiEntityResponseWrapper}
import shared.com.ortb.persistent.schema.Tables._
import shared.com.ortb.webapi.traits.{RestWebApi, RestWebApiBodyProcessor, RestWebApiJson, RestWebApiMessages}
import shared.io.logger.AppLogger
import javax.inject.Inject
import play.api.mvc._
import services.traits.ServiceBasicPersistentOperations
import shared.com.ortb.webapi.traits._
import shared.io.logger.AppLogger

abstract class RestWebApiDefinition[TTable, TRow, TKey] @Inject()
(components : ControllerComponents)
  extends
    AbstractController(components) with
    RestWebApi[TTable, TRow, TKey] with
    RestWebApiBodyProcessor[TTable, TRow, TKey] with
    RestWebApiMessages[TTable, TRow, TKey] with
    RestWebApiJson[TTable, TRow, TKey] {

  val service : ServiceBasicPersistentOperations[TTable, TRow, TKey]

  def getAll : Action[AnyContent] = Action { implicit request =>
    val campaigns = service.getAll
    val json = campaigns.asJson.spaces2
    Ok(json)
  }

  def byId(id : TKey) : Action[AnyContent] = Action { implicit request =>
    val entity = service.getById(id)
    val json = entity.get.asJson.spaces2
    Ok(json)
  }

  def add() : Action[AnyContent] = Action { implicit request =>
    try {
      val entityResponseWrapper = bodyRequestToEntity(request);
      val isDefinedProperly = entityResponseWrapper.isDefined &&
        entityResponseWrapper.get.entityWrapper.isDefined

      if (isDefinedProperly) {
        // has item
        val entityWrapper = entityResponseWrapper.get.entityWrapper.get
        val entity = entityWrapper.entity
        val response = service.add(entity)

        if (response.isSuccess && response.entity.isDefined) {
          performOkayOnEntity(
            Some(entity),
            successMessage()
          )
        }
      } else {
        performBadRequest()
      }
    } catch {
      case e : Exception => AppLogger.error(e)
        performBadRequest(
          e.toString,)
    }
  }

  def update(id : TKey) : Action[AnyContent]

  def delete(id    : TKey) : Action[AnyContent]

  def addEntities() : Action[AnyContent]

  def addEntitiesBySinge() : Action[AnyContent]
}
