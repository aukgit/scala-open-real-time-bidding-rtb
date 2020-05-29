package shared.com.ortb.controllers.implementations

import akka.util.ByteString
import play.api.http.{ HttpEntity, Status }
import play.api.mvc.{ Action, _ }
import play.mvc.Http.MimeTypes
import shared.io.extensions.TypeConvertExtensions._

class WebApiResponseImplementation(
  val controller : AbstractController,
  val components : ControllerComponents) {
  def okJson(jsonString : String) : Action[AnyContent] = components.actionBuilder(
    controller
      .Ok(jsonString)
      .as(MimeTypes.JSON)
  )

  def okJsonWithStatus(
    jsonString : String,
    statusCode : Int = Status.OK,
    contentType : String = MimeTypes.JSON
  ) : Action[AnyContent] = okJsonWithHeader(
    jsonString,
    ResponseHeader(statusCode),
    contentType)

  def okJsonWithHeader(
    jsonString : String,
    responseHeader : ResponseHeader = ResponseHeader(Status.OK),
    contentType : String = MimeTypes.JSON
  ) : Action[AnyContent] = controller.Action {
    Result(
      header = responseHeader,
      body = HttpEntity.Strict(ByteString(jsonString), contentType.toSome)
    )
  }

  def okTextWithStatus(
    jsonString : String,
    statusCode : Int = Status.OK,
    contentType : String = MimeTypes.TEXT
  ) : Action[AnyContent] = okJsonWithHeader(
    jsonString,
    ResponseHeader(statusCode),
    contentType)

  def okHtmlWithStatus(
    jsonString : String,
    statusCode : Int = Status.OK,
    contentType : String = MimeTypes.HTML
  ) : Action[AnyContent] = okJsonWithHeader(
    jsonString,
    ResponseHeader(statusCode),
    contentType)
}
