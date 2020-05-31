package shared.com.ortb.serverAkka

import akka.http.scaladsl.model.{ ContentTypes, HttpEntity, HttpResponse, StatusCodes }
import shared.com.ortb.model.requests.AkkaRequestModel
import shared.com.ortb.serverAkka.traits.akkaMethods.AkkaGetMethod

class AkkaMessageGetConcreteMethod(
  getMessage : String = "Hello World, GET",
  val additionalEndPointSuffix : String = "") extends AkkaGetMethod {
  override def get(akkaRequest : AkkaRequestModel) : HttpResponse = HttpResponse(
    status = StatusCodes.Accepted,
    entity = HttpEntity(
      ContentTypes.`application/json`,
      getMessage
    ))
}
