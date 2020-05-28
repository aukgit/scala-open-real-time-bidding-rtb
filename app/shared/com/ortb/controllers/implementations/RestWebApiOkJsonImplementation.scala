package shared.com.ortb.controllers.implementations

import shared.io.extensions.TypeConvertExtensions._
import play.api.http.HttpEntity
import play.api.mvc.{ AbstractController, ResponseHeader, Result }
import play.mvc.Http.MimeTypes

class RestWebApiOkJsonImplementation(controller : AbstractController) {
  def OkJson(jsonString : String) : Result = controller.Ok(jsonString).as(MimeTypes.JSON)

  def OkJsonWithHeader(
    jsonString : String,
    responseHeader : ResponseHeader,
    contentType : String = MimeTypes.JSON
  ) : Result = Result(
    header = responseHeader,
    body = HttpEntity.Strict(, contentType.toSome)
  )
}
