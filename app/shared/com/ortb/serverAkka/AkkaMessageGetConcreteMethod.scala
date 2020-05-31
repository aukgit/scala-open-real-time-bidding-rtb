package shared.com.ortb.serverAkka

import akka.http.scaladsl.model.{ ContentTypes, HttpEntity, HttpResponse, StatusCodes }
import shared.com.ortb.model.requests.AkkaRequestModel
import shared.com.ortb.serverAkka.traits.akkaMethods.AkkaGetMethod
import shared.io.extensions.TypeConvertExtensions._

class AkkaMessageGetConcreteMethod(
  getMessage : String = "Hello World, GET",
  val additionalEndPointSuffix : String = "") extends AkkaGetMethod {
  override def get(akkaRequest : AkkaRequestModel) : HttpResponse = {
    val entityString = akkaRequest.entityString
    val queryString = akkaRequest.queryStrings
    val content = s"""
                     |$getMessage
                     |Request Body:
                     |$entityString
                     |Query Strings:
                     |$queryString
                     |Headers:${ akkaRequest.headers.toJoinStringLineSeparator }
                     |""".stripMargin

    HttpResponse(
      status = StatusCodes.Accepted,
      entity = HttpEntity(
        ContentTypes.`application/json`,
        content
      ))
  }
}
