package shared.com.ortb.serverAkka

import akka.http.scaladsl.model._
import akka.http.scaladsl.unmarshalling.Unmarshal
import shared.com.ortb.model.requests.AkkaRequestModel
import shared.com.ortb.serverAkka.traits.akkaMethods.AkkaPostMethod
import shared.io.extensions.TypeConvertExtensions._
import shared.com.ortb.serverAkka.framework.restClient.softler.context.AkkaHttpContext._

class AkkaMessagePostConcreteMethod(
  postMessage : String = "Hello World, POST",
  val additionalEndPointSuffix : String = "") extends AkkaPostMethod {
  override def post(akkaRequest : AkkaRequestModel) : HttpResponse = {
    val entityString = akkaRequest.entityString

    HttpResponse(
      status = StatusCodes.Accepted,
      entity = HttpEntity(
        ContentTypes.`application/json`,
        s"""
           | $postMessage
           | Request Body:
           | $entityString
           | Headers:${ akkaRequest.headers.toJoinStringLineSeparator }
           | """.stripMargin
      ))
  }
}
