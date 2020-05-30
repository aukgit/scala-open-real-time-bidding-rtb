package shared.com.ortb.serverAkka.traits

import shared.io.extensions.TypeConvertExtensions._
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ HttpRequest, HttpResponse }
import shared.com.ortb.controllers.traits.AdditionalConfigBasedLogger
import shared.com.ortb.serverAkka.framework.restClient.softler.context.{ AkkaHttpContext, AkkaHttpRequest, AkkaHttpResponse, AkkaHttpResponseHandler }

import scala.concurrent.Future

trait AkkHttpServerContracts
  extends AkkaHttpRequest
    with AkkaHttpResponse
    with AkkaHttpResponseHandler
    with AkkaHttpContext
    with AdditionalConfigBasedLogger
    with ServiceProperties
    with ServerRun {

  val apiPrefixEndPoint : String
  def requestHandler : HttpRequest => Future[HttpResponse]

  override def serverRunAt(port : Int = serviceModel.port) : Unit = {
    val domain = serviceModel.domain.getOrElse(serverConfig.commonDomain)
    val portSelected = port.getOnZeroOrNegative(serviceModel.port)

    log(s"Server Starting (Title : ${ serviceModel.title }, Description: ${ serviceModel.description })", s"Domain: $domain, Port: $portSelected, ")
    log("routing", s"http://$domain:$portSelected$apiPrefixEndPoint$endPointPrefixes")
    Http().bindAndHandleAsync(requestHandler, domain, portSelected)
  }
}
