package shared.com.ortb.serverAkka.traits.akkaMethods

import akka.http.scaladsl.model.HttpResponse
import shared.com.ortb.controllers.traits.ConfigBasedLogger
import shared.com.ortb.manager.traits.CreateDefaultContext
import shared.com.ortb.model.requests.AkkaRequestModel

import scala.concurrent.Future

trait AkkaPostMethod extends AkkaMethodEssentials {
  val isPostImplemented : Boolean

  def post(akkaRequest : AkkaRequestModel) : HttpResponse

  def postEventual(akkaRequest : AkkaRequestModel) : Future[HttpResponse] = Future {
    ConfigBasedLogger.log(s"POST : ${ akkaRequest.uri.toString() }")
    ConfigBasedLogger.log(s"POST Request : ${ akkaRequest.entityString }")
    post(akkaRequest)
  }(CreateDefaultContext.createDefaultContext())
}
