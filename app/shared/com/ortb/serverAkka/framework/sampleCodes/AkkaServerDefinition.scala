package shared.com.ortb.serverAkka.framework.sampleCodes

import akka.http.scaladsl.model._
import shared.com.ortb.model.config.core.ServiceBaseModel
import shared.com.ortb.model.requests.AkkaRequestModel
import shared.com.ortb.serverAkka.traits.AkkHttpServerContracts
import shared.com.ortb.serverAkka.traits.akkaMethods.AkkaGetPostMethod
import shared.io.extensions.TypeConvertExtensions._

import scala.concurrent.Future

class AkkaServerDefinition(
  val serviceModel : ServiceBaseModel,
  val akkaGetPostMethod : AkkaGetPostMethod,
  val apiPrefixEndPoint : String = "api")
  extends AkkHttpServerContracts {

  def requestHandler : HttpRequest => Future[HttpResponse] = {

    return {
      case HttpRequest(HttpMethods.GET, uri @ Uri.Path(s"/${apiPrefixEndPoint}/$endPointPrefixes/service-name"), seqHeaders, entity, _) =>
        HttpResponse(
          status = StatusCodes.Accepted,
          entity = HttpEntity(
            ContentTypes.`application/json`,
            s"${ serviceModel.title } : ${ serviceModel.description }"
          )).toFuture

      case HttpRequest(HttpMethods.POST, uri @ Uri.Path(s"/${apiPrefixEndPoint}/$endPointPrefixes/service-name"), seqHeaders, entity, _) =>
        HttpResponse(
          status = StatusCodes.Accepted,
          entity = HttpEntity(
            ContentTypes.`application/json`,
            s"${ serviceModel.title } : ${ serviceModel.description }"
          )).toFuture

      case HttpRequest(HttpMethods.POST, uri @ Uri.Path(s"/$apiPrefixEndPoint/$endPointPrefixes/$additionalEndPointSuffix"), seqHeaders, entity, _) =>
        lazy val akkaRequest = AkkaRequestModel(endPointPrefixes, uri, seqHeaders, entity)
        akkaGetPostMethod.postEventual(akkaRequest)

      case HttpRequest(HttpMethods.GET, uri @ Uri.Path(s"/$apiPrefixEndPoint/$endPointPrefixes/$additionalEndPointSuffix"), seqHeaders, entity, _) =>
        lazy val akkaRequest = AkkaRequestModel(endPointPrefixes, uri, seqHeaders, entity)
        akkaGetPostMethod.getEventual(akkaRequest)

      case request : HttpRequest =>
        request.discardEntityBytes()
        HttpResponse(status = StatusCodes.NotFound).toFuture
    }
  }
}
