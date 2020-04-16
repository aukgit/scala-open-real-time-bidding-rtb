package controllers.api

import javax.inject._
import models.Todo
import play.api.libs.json._
import play.api.mvc._
import services.TodoService

import scala.concurrent.ExecutionContext.Implicits.global


class TodoController @Inject()(
  cc          : ControllerComponents,
  todoService : TodoService
) extends AbstractController(cc) {

  implicit val todoFormat = Json.format[Todo]

  def getAll() = Action.async { implicit request : Request[AnyContent] =>
    todoService.listAllItems map { items =>
      Ok(Json.toJson(items))
    }
  }

  def getById(id : Long) = Action.async { implicit request : Request[AnyContent] =>
    todoService.getItem(id) map { item =>
      Ok(Json.toJson(item))
    }
  }
}
