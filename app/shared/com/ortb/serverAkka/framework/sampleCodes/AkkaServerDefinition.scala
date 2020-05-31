package shared.com.ortb.serverAkka.framework.sampleCodes

import akka.http.scaladsl.model.{ HttpRequest, _ }
import shared.com.ortb.model.config.core.ServiceBaseModel
import shared.com.ortb.model.requests.AkkaRequestModel
import shared.com.ortb.serverAkka.traits.AkkHttpServerContracts
import shared.com.ortb.serverAkka.traits.akkaMethods.AkkaRequestHandlerGetPostMethods
import shared.io.extensions.TypeConvertExtensions._

import scala.concurrent.Future

class AkkaServerDefinition(
  val serviceModel : ServiceBaseModel,
  val akkaGetPostMethods : AkkaRequestHandlerGetPostMethods,
  val apiPrefixEndPoint : String = "api")
  extends AkkHttpServerContracts {

  lazy val requestHandler : HttpRequest => Future[HttpResponse] = processHttpRequest

  def processHttpRequest(httpRequest : HttpRequest) : Future[HttpResponse] = {
    lazy val akkaRequest = AkkaRequestModel(httpRequest)
    log(s"${ akkaRequest.httpMethodName } : Requested Path", akkaRequest.fullPath)
    val response = getMatchedAkkaMethod(akkaRequest)

    if (response.isDefined) {
      return response.get
    }

    akkaRequest.fullPath match {
      case s"/" =>
        throw new NotImplementedError()
    }
  }

  protected def getMatchedAkkaMethod(akkaRequest : AkkaRequestModel) : Option[Future[HttpResponse]] = {
    val path = akkaRequest.fullPath
    if (!methodsMapping.contains(path)) {
      return None
    }

    val method = methodsMapping(path)

    if (akkaRequest.isHttpGetMethod) {
      return method.getEventual(akkaRequest).toSome
    } else if (akkaRequest.isHttpPostMethod) {
      return method.postEventual(akkaRequest).toSome
    }

    None
  }
}
