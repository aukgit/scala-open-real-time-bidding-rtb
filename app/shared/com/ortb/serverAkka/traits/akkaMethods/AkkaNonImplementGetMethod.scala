package shared.com.ortb.serverAkka.traits.akkaMethods

import akka.http.scaladsl.model.HttpResponse
import shared.com.ortb.model.requests.AkkaRequestModel

class AkkaNonImplementGetMethod extends AkkaGetMethod {
  lazy override val isGetImplemented : Boolean = false
  lazy override val additionalEndPointSuffix : String = ""
  lazy override val isMethodImplemented : Boolean = false

  def get(akkaRequest : AkkaRequestModel) : HttpResponse =
    HttpResponse(
      akka.http.scaladsl.model.StatusCodes.NotImplemented,
      entity = s"GET ${ akkaRequest.relativePath } not supported.")

}
