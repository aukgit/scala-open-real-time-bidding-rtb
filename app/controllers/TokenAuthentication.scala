package controllers

import javax.inject.Inject
import play.api.mvc._
import play.api.libs.json.Json
import play.api.http.HeaderNames
import scala.concurrent.{ExecutionContext, Future}

import models.User
import repositories.UserRepository

case class AuthRequest[A](user: User, request: Request[A])
    extends WrappedRequest[A](request)

class TokenAuthentication @Inject()(
    bodyParser: BodyParsers.Default,
    usersRepo: UserRepository
)(implicit ec: ExecutionContext)
    extends ActionBuilder[AuthRequest, AnyContent] {
  override protected def executionContext: ExecutionContext = ec
  override def parser: BodyParser[AnyContent] = bodyParser

  def extractToken(header: String) = {
    header.split(" ") match {
      case Array(_, token) => Some(token)
      case _               => None
    }
  }

  override def invokeBlock[A](
      request: Request[A],
      block: AuthRequest[A] => Future[Result]
  ): Future[Result] = {
    request.headers.get(HeaderNames.AUTHORIZATION) match {
      case Some(token) =>
        extractToken(token) match {
          case Some(t) =>
            usersRepo.findByToken(t) flatMap {
              case Some(user) => block(AuthRequest(user, request))
              case _ =>
                Future.successful(
                  Results.Unauthorized(
                    Json.obj(
                      "success" -> false,
                      "error" -> "Invalid access token"
                    )
                  )
                )
            }
          case None =>
            Future.successful(
              Results.Unauthorized(
                Json.obj("success" -> false, "error" -> "Invalid access token")
              )
            )
        }
      case None =>
        Future.successful(
          Results.Unauthorized(
            Json.obj("success" -> false, "error" -> "Invalid access token")
          )
        )
    }
  }
}
