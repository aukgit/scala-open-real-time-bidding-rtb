package shared.com.ortb.serverAkka.framework.restClient.softler.client

import akka.http.scaladsl.Http
import akka.stream.Materializer
import akka.http.scaladsl.unmarshalling.Unmarshaller

import scala.concurrent.{Future, ExecutionContext}
import akka.http.scaladsl.model.{HttpRequest, ResponseEntity, HttpMethods}
import akka.actor.ActorSystem
import shared.com.ortb.serverAkka.framework.restClient.softler.processor.ResponseProcessor

trait UnsafeMethods[R <: RequestState] extends
  AkkaHttpRequest {

  import shared.com.ortb.serverAkka.framework.restClient.softler.client.RequestState._

  def post()(
    implicit evidence : RequestWithEntity[R],
    system : ActorSystem,
    materializer : Materializer,
    executionContext : ExecutionContext) : Future[ClientResponse] =
    ClientResponse(Http().singleRequest(postRequest))

  private def postRequest : HttpRequest = request.copy(method = HttpMethods.POST)

  def post[A](
    implicit evidence : RequestWithEntity[R],
    system : ActorSystem,
    materializer : Materializer,
    executionContext : ExecutionContext,
    um : Unmarshaller[ResponseEntity, A],
    processor : ResponseProcessor) : Future[A] = ClientResponse.as[A](
    Http().singleRequest(postRequest)
    )

  def put()(
    implicit evidence : RequestWithEntity[R],
    system : ActorSystem,
    materializer : Materializer,
    executionContext : ExecutionContext) : Future[ClientResponse] =
    ClientResponse(Http().singleRequest(putRequest))

  private def putRequest : HttpRequest = request.copy(method = HttpMethods.PUT)

  def put[A](
    implicit evidence : RequestWithEntity[R],
    system : ActorSystem,
    materializer : Materializer,
    executionContext : ExecutionContext,
    um : Unmarshaller[ResponseEntity, A],
    processor : ResponseProcessor) : Future[A] = ClientResponse.as[A](
    Http().singleRequest(putRequest)
    )

  def patch()(
    implicit evidence : RequestWithEntity[R],
    system : ActorSystem,
    materializer : Materializer,
    executionContext : ExecutionContext) : Future[ClientResponse] =
    ClientResponse(Http().singleRequest(patchRequest))

  def patch[A](
    implicit evidence : RequestWithEntity[R],
    system : ActorSystem,
    materializer : Materializer,
    executionContext : ExecutionContext,
    um : Unmarshaller[ResponseEntity, A],
    processor : ResponseProcessor) : Future[A] = ClientResponse.as[A](
    Http().singleRequest(patchRequest)
    )

  private def patchRequest : HttpRequest = request.copy(method = HttpMethods.PATCH)
}
