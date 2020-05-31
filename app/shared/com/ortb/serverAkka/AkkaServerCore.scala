package shared.com.ortb.serverAkka

import akka.http.scaladsl.model._
import shared.com.ortb.model.config.core.ServiceBaseModel
import shared.com.ortb.model.requests.AkkaRequestModel
import shared.com.ortb.serverAkka.traits.AkkHttpServerContracts
import shared.com.ortb.serverAkka.traits.akkaMethods.AkkaGetPostMethod

import scala.concurrent.Future

//
//class AkkaServerCore(
//  val serviceModel : ServiceBaseModel,
//  val akkaGetPostMethod : AkkaGetPostMethod,
//  val apiPrefixEndPoint : String = "/api/")
//  extends AkkHttpServerContracts {
//
//  def requestHandler : HttpRequest => Future[HttpResponse] = {
//    case HttpRequest(HttpMethods.POST, uri @ Uri.Path(s"$apiPrefixEndPoint$endPointPrefixes"), seqHeaders, entity, _) =>
//      lazy val akkaRequest = AkkaRequestModel(endPointPrefixes, uri, seqHeaders, entity)
//      akkaGetPostMethod.postEventual(akkaRequest)
//
//    case HttpRequest(HttpMethods.GET, uri @ Uri.Path(s"$apiPrefixEndPoint$endPointPrefixes"), seqHeaders, entity, _) =>
//      lazy val akkaRequest = AkkaRequestModel(endPointPrefixes, uri, seqHeaders, entity)
//      akkaGetPostMethod.getEventual(akkaRequest)
//
//    case request : HttpRequest =>
//      request.discardEntityBytes()
//      Future {
//        HttpResponse(status = StatusCodes.NotFound)
//      }
//  }
//}
