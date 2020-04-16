package controllers

import javax.inject.{Inject, Singleton}
import play.api.mvc.{AbstractController, ControllerComponents}
import play.api.libs.json.Json
import scala.concurrent.{Future, ExecutionContext}
import org.mindrot.jbcrypt.BCrypt

import models.{Auth, User}
import repositories.UserRepository

class UserController @Inject()(
    components: ControllerComponents,
    usersRepo: UserRepository,
    withToken: TokenAuthentication
)(implicit ec: ExecutionContext)
    extends AbstractController(components) {
  def listUsers = withToken.async { _ =>
    usersRepo.all.map { users =>
      Ok(Json.toJson(users))
    }
  }

  def findByToken(token: String) = Action.async { _ =>
    usersRepo.findByToken(token).map { user =>
      Ok(Json.toJson(user))
    }
  }

  def register = Action.async(parse.json) {
    _.body
      .validate[Auth]
      .map { user =>
        usersRepo
          .register(user)
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
                    // "error" -> e.toString() // for debugging
                    "error" -> "Something went wrong"
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

  def login = Action.async(parse.json) {
    _.body
      .validate[Auth]
      .map { user =>
        usersRepo.login(user).map { result =>
          result match {
            case Some(data) => {
              if (BCrypt.checkpw(user.password, data.password)) {
                Ok(Json.toJson(data))
              } else
                BadRequest(
                  Json.obj(
                    "status" -> "failed",
                    "error" -> "Invalid username/password"
                  )
                )
            }
            case None =>
              BadRequest(
                Json.obj(
                  "status" -> "failed",
                  "error" -> "Invalid username/password"
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
