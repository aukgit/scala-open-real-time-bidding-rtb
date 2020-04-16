package controllers

import javax.inject.{Inject, Singleton}
import play.api.mvc.{AbstractController, ControllerComponents}
import play.api.libs.json.Json

class ApiController @Inject()(
    components: ControllerComponents
) extends AbstractController(components) {
  def index = Action { implicit request =>
    Ok(Json.obj("hello" -> "world"))
  }

  def ping = Action { implicit request =>
    Ok(Json.obj("ping" -> true))
  }
}
