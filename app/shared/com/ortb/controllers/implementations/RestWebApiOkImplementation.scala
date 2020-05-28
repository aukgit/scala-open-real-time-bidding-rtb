package shared.com.ortb.controllers.implementations

import akka.util.ByteString
import shared.io.extensions.TypeConvertExtensions._
import play.api.http.{ HttpEntity, Status, Writeable }
import play.api.mvc.{ AbstractController, ResponseHeader, Result }
import play.mvc.Http.MimeTypes

class RestWebApiOkImplementation(controller : AbstractController) {
  def okJson(jsonString : String) : Result = controller.Ok(jsonString).as(MimeTypes.JSON)

  def okJsonWithStatus(
    jsonString : String,
    statusCode : Int = Status.OK,
    contentType : String = MimeTypes.JSON
  ) : Result = okJsonWithHeader(
    jsonString,
    ResponseHeader(statusCode),
    contentType)

  def okTextWithStatus(
    jsonString : String,
    statusCode : Int = Status.OK,
    contentType : String = MimeTypes.TEXT
  ) : Result = okJsonWithHeader(
    jsonString,
    ResponseHeader(statusCode),
    contentType)

  def okJsonWithHeader(
    jsonString : String,
    responseHeader : ResponseHeader = ResponseHeader(Status.OK),
    contentType : String = MimeTypes.JSON
  ) : Result = Result(
    header = responseHeader,
    body = HttpEntity.Strict(ByteString(jsonString), contentType.toSome)
  )

  def okHtmlWithStatus(
    jsonString : String,
    statusCode : Int = Status.OK,
    contentType : String = MimeTypes.HTML
  ) : Result = okJsonWithHeader(
    jsonString,
    ResponseHeader(statusCode),
    contentType)
}
