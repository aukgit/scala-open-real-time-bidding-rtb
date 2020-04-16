package controllers

import javax.inject.Inject
import play.api.libs.json.Json
import play.api.mvc._
import shared.com.ortb.manager.AppManager
import shared.com.ortb.persistent.Repositories
import io.circe._
import io.circe.generic.auto._
import io.circe.parser._
import io.circe.syntax._

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
    val json = campaigns.asJson.spaces2
    println(json)
    Ok(json)
  }
}
