package shared.com.ortb.serverAkka.framework.sampleCodes

import akka.http.scaladsl.model.{ HttpRequest, _ }
import shared.com.ortb.controllers.traits.ConfigBasedLogger
import shared.com.ortb.model.config.core.ServiceBaseModel
import shared.com.ortb.model.requests.AkkaRequestModel
import shared.com.ortb.model.routing.RoutingEnhancedModel
import shared.com.ortb.serverAkka.traits.AkkHttpServerContracts
import shared.com.ortb.serverAkka.traits.akkaMethods.AkkaRequestHandlerGetPostMethods
import shared.io.extensions.HttpExtensions._
import shared.io.extensions.TypeConvertExtensions._
import shared.io.loggers.AppLogger

import scala.concurrent.Future

class AkkaServerDefinition(
  val currentServiceModel : ServiceBaseModel,
  val akkaGetPostMethods : AkkaRequestHandlerGetPostMethods,
  val apiPrefixEndPoint : String = "api")
  extends AkkHttpServerContracts {

  lazy val requestHandler : HttpRequest => Future[HttpResponse] = processHttpRequest

  def processHttpRequest(httpRequest : HttpRequest) : Future[HttpResponse] = {
    lazy val akkaRequest = AkkaRequestModel(httpRequest, this)
    log(s"${ akkaRequest.httpMethodName } : Requested Path", akkaRequest.fullPath)
    val response = getMatchedAkkaMethod(akkaRequest)

    if (response.isDefined) {
      return response.get
    }

    getServiceCommonResponse(akkaRequest)
  }

  private def getServiceCommonResponse(akkaRequest : => AkkaRequestModel) : Future[HttpResponse] = {
    val enhancedRouting = RoutingEnhancedModel(akkaRequest)

    if (enhancedRouting.isCommandRoute) {
      return allRoutesJsonString.toHttpJsonResponseFuture()
    } else if (enhancedRouting.isCommandUrl) {
      return allRoutesUrlJsonString.toHttpJsonResponseFuture()
    } else if (enhancedRouting.isHelp) {
      // TODO fix the return type to HTML
      return currentServiceModel.help.toHttpJsonResponseFuture()
    }

    NotImplementedResponse(akkaRequest)
  }

  private def NotImplementedResponse(akkaRequest : => AkkaRequestModel) = {
    val message = s"""${ akkaRequest.httpMethodName } Request path : "${ akkaRequest.relativePath }" is not supported in the system.
                     |Available Possible Routes :
                     |$allRoutesUrlJsonString""".stripMargin

    HttpResponse(StatusCodes.BadRequest, entity = message).toFuture
  }

  def getResponseOfPlaceHolderRouting(akkaRequest : AkkaRequestModel) : Option[Future[HttpResponse]] = {
    log("PlaceHolder Routing Found", akkaRequest.routingPlaceHolderModel.placeHolderRoute)
    
  }

  protected def getMatchedAkkaMethod(akkaRequest : AkkaRequestModel) : Option[Future[HttpResponse]] = {
    val path = akkaRequest
      .relativePath
      .safeTrimForwardSlashBothEnds

    if(akkaRequest.routingPlaceHolderModel.hasPlaceHolderRouting) {
      return getResponseOfPlaceHolderRouting(akkaRequest)
    }

    if (!methodsMapping.contains(path)) {
      return None
    }

    val method = methodsMapping(path)

    if (!method.isMethodImplemented) {
      return None
    }

    if (akkaRequest.isHttpGetMethod && method.isGetImplemented) {
      return method.getEventual(akkaRequest).toSome
    } else if (akkaRequest.isHttpPostMethod && method.isPostImplemented) {
      return method.postEventual(akkaRequest).toSome
    }



    None
  }
}
