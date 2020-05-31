package shared.com.ortb.serverAkka.traits

import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ HttpRequest, HttpResponse }
import shared.com.ortb.controllers.traits.ConfigBasedLogger
import shared.com.ortb.serverAkka.framework.restClient.softler.context.{ AkkaHttpContext, AkkaHttpRequest, AkkaHttpResponse, AkkaHttpResponseHandler }
import shared.com.ortb.serverAkka.traits.akkaMethods.AkkaGetPostMethod
import shared.io.extensions.TypeConvertExtensions._

import scala.concurrent.Future

trait AkkHttpServerContracts
  extends AkkaHttpRequest
    with AkkaHttpResponse
    with AkkaHttpResponseHandler
    with AkkaHttpContext
    with ConfigBasedLogger
    with ServiceProperties
    with ServerRun {

  val apiPrefixEndPoint : String

  def requestHandler : HttpRequest => Future[HttpResponse]

  lazy val domainHostToString = s"Domain/Host: $finalDomainHost, Port: $servicePort"
  lazy val serviceDisplayName = s"Title : ${ serviceModel.title }, Description: ${ serviceModel.description }"
  lazy val additionalEndPointSuffix : String = akkaGetPostMethod
    .additionalEndPointSuffix
    .getIfExist(s"/${ akkaGetPostMethod.additionalEndPointSuffix }")
  lazy val hostSuffixEndpoint = s"/$apiPrefixEndPoint/$endPointPrefixes$additionalEndPointSuffix"
  lazy val hostFullEndpoint = s"http://$finalDomainHost:$servicePort$hostSuffixEndpoint"
  val akkaGetPostMethod : AkkaGetPostMethod

  override def serverRun() : Unit = {
    log(s"Server Starting ($serviceDisplayName)", domainHostToString)
    log("routing", hostFullEndpoint)
    lazy val bindingFuture = Http().bindAndHandleAsync(requestHandler, finalDomainHost, servicePort)
    bindingFuture.failed.foreach { ex =>
      val message = s"Failed to bind to $domainHostToString"
      logError(message, exception = ex)
    }
  }
}
