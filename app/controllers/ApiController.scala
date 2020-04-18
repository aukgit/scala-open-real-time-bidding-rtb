package controllers

import java.io.File

import javax.inject.Inject
import play.api.libs.json.Json
import play.api.mvc._
import shared.com.ortb.manager.AppManager
import shared.com.ortb.persistent.Repositories
import io.circe._
import io.circe.generic.auto._
import io.circe.parser._
import io.circe.syntax._
import play.api.{Environment, Play}
import shared.com.ortb.model.wrappers.http.HttpSuccessActionWrapper
import shared.io.helpers.PathHelper
import shared.io.logger.AppLogger

class ApiController @Inject()(

  components : ControllerComponents)
  extends AbstractController(components) {
  def index : Action[AnyContent] = Action { implicit request =>
    Ok(Json.obj("hello" -> "world"))
  }

  def ping : Action[AnyContent] = Action { implicit request =>
    Ok(Json.obj("ping" -> true))
  }

  def campaigns : Action[AnyContent] = Action { implicit request =>
    val appManager = new AppManager
    val repositories = new Repositories(appManager)
    val campaigns = repositories.campaignRepository.getAll.toArray
    AppLogger.logEntitiesNonFuture(isExecute = true, campaigns)
    val json = campaigns.asJson.spaces2
    println(json)
    Ok(json)
  }

  def resourcePath : Action[AnyContent] = Action { implicit request =>
    val path = PathHelper.getResourcePath
    val rootPath = Environment.simple().resource("")
    val js = Json.obj(
      ("path", path),
      ("Environment.simple().resource(\"\")", rootPath.toString),
    )

    println(js)
    AppLogger.debug("Hello Debu WWW g")
    Ok(js)
  }
}
