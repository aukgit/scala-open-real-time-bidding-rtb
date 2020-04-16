package controllers

import javax.inject.{Inject, Singleton}
import play.api.mvc.{AbstractController, ControllerComponents}
import play.api.libs.json.Json
import scala.concurrent.{Future, ExecutionContext}

import models.{Idea, IdeaDTO}
import repositories.IdeaRepository

class IdeaController @Inject()(
    components: ControllerComponents,
    ideasRepo: IdeaRepository,
    withToken: TokenAuthentication,
)(implicit ec: ExecutionContext)
    extends AbstractController(components) {
  def listIdeas = Action.async { _ =>
    ideasRepo.list.map { ideas =>
      Ok(Json.toJson(ideas))
    }
  }

  def createIdea = withToken.async(parse.json) { request => {
    request.body
      .validate[IdeaDTO]
      .map { idea =>
        ideasRepo
          .create(idea, request.user)
          .map { _ =>
            Created(
              Json.obj(
                "status" -> "success"
              )
            )
          }
          .recoverWith {
            case e =>
              Future {
                InternalServerError(
                  Json.obj(
                    "status" -> "failed",
                    "error" -> e.toString()
                  )
                )
              }
          }
      }
      .getOrElse(
        Future.successful(
          BadRequest(
            Json.obj(
              "status" -> "failed",
              "error" -> "Invalid format"
            )
          )
        )
      )
    }
  }

  def readIdea(id: Int) = Action.async { _ =>
    ideasRepo.read(id).map { result =>
      result match {
        case Some(idea) => Ok(Json.toJson(idea))
        case None =>
          NotFound(
            Json.obj(
              "status" -> "failed",
              "error" -> "Not Found"
            )
          )
      }
    }
  }

  def updateIdea(id: Int) = withToken.async(parse.json) { request => 
    request.body
      .validate[IdeaDTO]
      .map { idea =>
        ideasRepo
          .update(id, idea, request.user)
          .map { result =>
            result match {
              case 0 =>
                NotFound(
                  Json.obj(
                    "status" -> "failed",
                    "error" -> "Not Found"
                  )
                )
              case _ => Ok(Json.obj("status" -> "success"))
            }
          }
          .recoverWith {
            case e =>
              Future {
                InternalServerError(
                  Json.obj(
                    "status" -> "failed",
                    "error" -> e.toString()
                  )
                )
              }
          }
      }
      .getOrElse(
        Future.successful(
          BadRequest(
            Json.obj(
              "status" -> "failed",
              "error" -> "Invalid format"
            )
          )
        )
      )
  }

  def deleteIdea(id: Int) = withToken.async { request =>
    ideasRepo
      .delete(id, request.user)
      .map { result =>
        result match {
          case 0 =>
            NotFound(
              Json.obj(
                "status" -> "failed",
                "error" -> "Not Found"
              )
            )
          case _ => Ok(Json.obj("status" -> "success"))
        }
      }
      .recoverWith {
        case e =>
          Future {
            InternalServerError(
              Json.obj(
                "status" -> "failed",
                "error" -> e.toString()
              )
            )
          }
      }
  }
}
