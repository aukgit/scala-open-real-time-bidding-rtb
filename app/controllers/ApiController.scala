package controllers

import javax.inject.Inject
import play.api.libs.json.Json
import play.api.mvc._


class ApiController @Inject()(
  components : ControllerComponents)
  extends AbstractController(components) {
  def index : Action[AnyContent] = Action { implicit request =>
    Ok(Json.obj("hello" -> "world"))
  }

  def ping : Action[AnyContent] = Action { implicit request =>
    Ok(Json.obj("ping" -> true))
  }

//  def campaigns : Action[AnyContent] = Action { implicit request =>
//    Ok()
//  }
}
