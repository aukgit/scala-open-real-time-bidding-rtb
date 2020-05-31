package shared.com.ortb.serverAkka.traits

import akka.http.scaladsl.Http
import shared.com.ortb.controllers.traits.ConfigBasedLogger
import shared.com.ortb.serverAkka.framework.restClient.softler.context._

trait AkkHttpServerContracts
  extends AkkaHttpRequest
    with AkkaHttpResponse
    with AkkaHttpResponseHandler
    with AkkaHttpContext
    with ConfigBasedLogger
    with AkkaHttpServerProperties
    with ServerRun {

  override def serverRun() : Unit = {
    log(s"Server Starting ($serviceDisplayName)", domainHostToString)
    log("routing", hostFullEndpoint)
    lazy val bindingFuture = Http()
      .bindAndHandleAsync(requestHandler, finalDomainHost, servicePort)

    bindingFuture.failed.foreach { ex =>
      val message = s"Failed to bind to $domainHostToString"
      logError(message, exception = ex)
    }
  }
}
