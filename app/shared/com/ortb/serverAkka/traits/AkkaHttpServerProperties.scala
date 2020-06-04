package shared.com.ortb.serverAkka.traits

import akka.http.scaladsl.model.{ HttpRequest, HttpResponse }
import shared.com.ortb.serverAkka.traits.akkaMethods.{ AkkaGetPostMethod, AkkaRequestHandlerGetPostMethods }
import shared.io.extensions.HttpExtensions._
import shared.io.extensions.TypeConvertExtensions._

import scala.concurrent.Future

trait AkkaHttpServerProperties extends ServiceProperties {
  lazy val domainHostToString = s"Domain/Host: $finalDomainHost, Port: $servicePort"
  lazy val serviceDisplayName = s"Title : ${ currentServiceModel.title }, Description: ${ currentServiceModel.description }"
  lazy val routingPrefix = s"/$apiPrefixEndPoint/${ serverConfig.prefixRouting }/$endPointPrefixes"
  lazy val hostEndpointPrefix = s"http://$finalDomainHost:$servicePort"
  lazy val hostFullEndpoint = s"$hostEndpointPrefix$routingPrefix"
  lazy val methodsMapping : Map[String, AkkaGetPostMethod] = akkaGetPostMethods
    .akkaMethodsMap
    .map(w => s"$routingPrefix/${ w._1 }".safeTrimForwardSlashBothEnds -> w._2)

  lazy val implementedRoutes : Iterable[String] = methodsMapping.keys
  lazy val possibleRoutes : Iterable[String] = currentServiceModel.routing
  lazy val allRoutes : List[String] = implementedRoutes.concat(possibleRoutes).toList.distinct
  lazy val allRoutesJsonString : String = allRoutes.toJsonStringPretty
  lazy val allRoutesUrlJsonString : String = allRoutes
    .map(w => hostEndpointPrefix.combineWithForwardSlash(w))
    .toJsonStringPretty

  val apiPrefixEndPoint : String
  val akkaGetPostMethods : AkkaRequestHandlerGetPostMethods
  val requestHandler : HttpRequest => Future[HttpResponse]
}
