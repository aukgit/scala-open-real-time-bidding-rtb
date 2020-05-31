package shared.com.ortb.serverAkka.traits.akkaMethods

import akka.http.scaladsl.model.HttpResponse
import shared.com.ortb.controllers.traits.ConfigBasedLogger
import shared.com.ortb.manager.traits.CreateDefaultContext
import shared.com.ortb.model.requests.AkkaRequestModel

import scala.concurrent.Future

trait AkkaGetMethod extends AkkaMethodEssentials {

  def get(akkaRequest : AkkaRequestModel) : HttpResponse

  def getEventual(akkaRequest : AkkaRequestModel) : Future[HttpResponse] =
    Future {
      ConfigBasedLogger.log(s"GET : ${ akkaRequest.uri.path.toString() }")
      ConfigBasedLogger.log(s"GET Request : ${ akkaRequest.entityString }")
      get(akkaRequest)
    }(CreateDefaultContext.createDefaultContext())
}
