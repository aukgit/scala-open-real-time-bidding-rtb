package shared.com.ortb.serverAkka.traits

import akka.http.scaladsl.model.{ HttpRequest, HttpResponse }
import shared.com.ortb.serverAkka.traits.akkaMethods.{ AkkaGetPostMethod, AkkaRequestHandlerGetPostMethods }
import shared.io.extensions.HttpExtensions._
import shared.io.extensions.TypeConvertExtensions._
import shared.io.helpers.PathHelper

import scala.concurrent.Future

trait AkkaHttpServerProperties extends ServiceProperties {
  lazy val domainHostToString = s"Domain/Host: $finalDomainHost, Port: $servicePort"
  lazy val serviceDisplayName = s"Title : ${ currentServiceModel.title }, Description: ${ currentServiceModel.description }"
  lazy val routingPrefix = s"/$apiPrefixEndPoint/${ serverConfig.prefixRouting }/$endPointPrefixes"
  lazy val hostEndpointPrefix = s"http://$finalDomainHost:$servicePort"
  lazy val hostFullEndpoint = s"$hostEndpointPrefix$routingPrefix"
  lazy val methodsMapping : Map[String, AkkaGetPostMethod] = akkaGetPostMethods
    .akkaMethodsMap
    .map(w => getRouteRelativePath(w._1) -> w._2)

  lazy val implementedRoutes : Iterable[String] = methodsMapping.keys
  lazy val currentServicePossibleRoutes : Iterable[String] = currentServiceModel
    .routing
    .map(w => getRouteRelativePath(w))

  lazy val allRoutes : List[String] = implementedRoutes
    .concat(currentServicePossibleRoutes)
    .concat(globalRoutePrefixes)
    .toList
    .sortBy(w => w)
    .distinct

  lazy val placeHoldersRouting : Map[String, String] = allRoutes
    .filter(w => w.contains(PathHelper.routingPlaceHolderStarting))
    .map(w => PathHelper.extractFirstPartOfPlaceHolderRouting(w) -> w)
    .toMap

  lazy val allRoutesJsonString : String = allRoutes.toJsonStringPretty
  lazy val allRoutesUrlJsonString : String = allRoutes
    .map(w => hostEndpointPrefix.combineWithForwardSlash(w))
    .toJsonStringPretty

  lazy private val globalRoutePrefixes = serverConfig
    .serviceGlobalRoutesPrefixes
    .map(w => getRouteRelativePath(w))


  val apiPrefixEndPoint : String
  val akkaGetPostMethods : AkkaRequestHandlerGetPostMethods
  val requestHandler : HttpRequest => Future[HttpResponse]

  private def getRouteRelativePath(routeSuffixEnding : String) =
    s"$routingPrefix/${ routeSuffixEnding }".safeTrimForwardSlashBothEnds
}
