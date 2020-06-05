package shared.com.ortb.serverAkka.implementations

import akka.http.scaladsl.model.{ ContentTypes, HttpEntity, HttpResponse, StatusCodes }
import shared.com.ortb.model.requests.AkkaRequestModel
import shared.com.ortb.serverAkka.traits.akkaMethods.AkkaGetMethod

class AkkaMonitorStartServiceConcreteGetMethod extends AkkaGetMethod {
  lazy override val isGetImplemented : Boolean = true
  lazy override val isMethodImplemented : Boolean = true

  override def get(akkaRequest : AkkaRequestModel) : HttpResponse = {
    HttpResponse(
      status = StatusCodes.Accepted,
      entity = HttpEntity(
        ContentTypes.`application/json`,
        "start-services"
      ))
  }
}
