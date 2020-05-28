package shared.com.ortb.controllers.traits

import play.api.http.Status
import play.api.mvc.{ AbstractController, ResponseHeader, Result }
import play.mvc.Http.MimeTypes

trait WebApiResult {
  lazy val noContent : Result = controller.NoContent.as(MimeTypes.JSON)
  val controller : AbstractController

  def okJson(jsonString : String) : Result

  def okJsonWithStatus(
    jsonString : String,
    statusCode : Int = Status.OK,
    contentType : String = MimeTypes.JSON
  ) : Result

  def okTextWithStatus(
    jsonString : String,
    statusCode : Int = Status.OK,
    contentType : String = MimeTypes.TEXT
  ) : Result

  def okJsonWithHeader(
    jsonString : String,
    responseHeader : ResponseHeader = ResponseHeader(Status.OK),
    contentType : String = MimeTypes.JSON
  ) : Result

  def okHtmlWithStatus(
    jsonString : String,
    statusCode : Int = Status.OK,
    contentType : String = MimeTypes.HTML
  ) : Result
}
