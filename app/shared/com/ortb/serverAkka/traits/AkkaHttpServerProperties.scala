package shared.com.ortb.serverAkka.traits

import akka.http.scaladsl.model.{ HttpRequest, HttpResponse }
import shared.com.ortb.serverAkka.traits.akkaMethods.{ AkkaGetPostMethod, AkkaRequestHandlerGetPostMethods }

import scala.concurrent.Future

trait AkkaHttpServerProperties extends ServiceProperties {
  lazy val domainHostToString = s"Domain/Host: $finalDomainHost, Port: $servicePort"
  lazy val serviceDisplayName = s"Title : ${ serviceModel.title }, Description: ${ serviceModel.description }"
  lazy val hostSuffixEndpoint = s"/$apiPrefixEndPoint/$endPointPrefixes"
  lazy val hostFullEndpoint = s"http://$finalDomainHost:$servicePort$hostSuffixEndpoint"
  lazy val methodsMapping : Map[String, AkkaGetPostMethod] = akkaGetPostMethods
    .akkaMethodsMap
    .map(w => s"$hostSuffixEndpoint/${ w._1 }" -> w._2)
  val apiPrefixEndPoint : String
  val akkaGetPostMethods : AkkaRequestHandlerGetPostMethods
  val requestHandler : HttpRequest => Future[HttpResponse]
}
