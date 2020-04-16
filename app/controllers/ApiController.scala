package controllers

import javax.inject.{Inject, Singleton}
import play.api.mvc.{
  AbstractController,
  Action,
  AnyContent,
  ControllerComponents
}
import play.api.libs.json.Json

class ApiController @Inject()(components: ControllerComponents)
    extends AbstractController(components) {
  def index: Action[AnyContent] = Action { implicit request =>
    Ok(Json.obj("hello" -> "world"))
  }

  def ping: Action[AnyContent] = Action { implicit request =>
    Ok(Json.obj("ping" -> true))
  }
}
