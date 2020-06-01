package shared.com.ortb.serverAkka.traits.akkaMethods

import akka.http.scaladsl.model._
import shared.com.ortb.model.requests.AkkaRequestModel

class AkkaNonImplementPostMethod extends AkkaPostMethod {
  lazy override val isPostImplemented : Boolean = false
  lazy override val additionalEndPointSuffix : String = ""
  lazy override val isMethodImplemented : Boolean = false

  def post(akkaRequest : AkkaRequestModel) : HttpResponse =
    HttpResponse(
      StatusCodes.NotImplemented,
      entity = s"POST ${ akkaRequest.relativePath } not supported.")
}
