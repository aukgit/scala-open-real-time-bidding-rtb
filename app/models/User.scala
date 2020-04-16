package models

import javax.inject.Inject
import play.api.libs.json._
import java.sql.Date
import pdi.jwt.{JwtJson, JwtAlgorithm}

case class User(
    id: Option[Int],
    username: String,
    password: String,
    created_date: Date
)

object User {
  implicit val userWrites: Writes[User] = Writes { user =>
    Json.obj(
      "id" -> user.id,
      "username" -> user.username,
      "created_date" -> user.created_date.toString,
      "token" -> JwtJson.encode(
        Json.obj("id" -> user.id, "username" -> user.username),
        "secretKey",
        JwtAlgorithm.HS256
      )
    )
  }

  def fromAuth(auth: Auth): User =
    new User(
      None,
      auth.username,
      auth.password,
      new Date(new java.util.Date().getTime())
    )
}

case class Auth(username: String, password: String)

object Auth {
  implicit val authFormat: OFormat[Auth] = Json.format[Auth]
}
