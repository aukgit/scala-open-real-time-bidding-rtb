package shared.com.ortb.controllers.traits

import play.api.http.Status
import play.api.mvc.{ AbstractController, Action, AnyContent, ResponseHeader, Result }
import play.mvc.Http.MimeTypes

trait WebApiResult {
  lazy val noContent : Result = controller.NoContent.as(MimeTypes.JSON)
  val controller : AbstractController

  def okJson(jsonString : String) : Action[AnyContent]

  def okJsonWithStatus(
    jsonString : String,
    statusCode : Int = Status.OK,
    contentType : String = MimeTypes.JSON
  ) : Action[AnyContent]

  def okTextWithStatus(
    jsonString : String,
    statusCode : Int = Status.OK,
    contentType : String = MimeTypes.TEXT
  ) : Action[AnyContent]

  def okJsonWithHeader(
    jsonString : String,
    responseHeader : ResponseHeader = ResponseHeader(Status.OK),
    contentType : String = MimeTypes.JSON
  ) : Action[AnyContent]

  def okHtmlWithStatus(
    jsonString : String,
    statusCode : Int = Status.OK,
    contentType : String = MimeTypes.HTML
  ) : Action[AnyContent]
}
