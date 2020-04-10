package com.ortb.serverAkka.framework.rest.softler.client

import akka.http.scaladsl.Http
import akka.stream.Materializer
import akka.http.scaladsl.unmarshalling.Unmarshaller

import scala.concurrent.{Future, ExecutionContext}
import akka.http.scaladsl.model.{HttpRequest, ResponseEntity, HttpMethods}
import akka.actor.ActorSystem
import com.ortb.serverAkka.framework.rest.softler.processor.ResponseProcessor

trait UnsafeMethods[R <: RequestState] extends AkkaHttpRequest {

  import com.ortb.serverAkka.framework.rest.softler.client.RequestState._

  private def postRequest: HttpRequest = request.copy(method = HttpMethods.POST)

  private def putRequest: HttpRequest = request.copy(method = HttpMethods.PUT)

  private def patchRequest: HttpRequest = request.copy(method = HttpMethods.PATCH)

  def post()(implicit evidence: RequestWithEntity[R],
             system: ActorSystem,
             materializer: Materializer,
             executionContext: ExecutionContext): Future[ClientResponse] =
    ClientResponse(Http().singleRequest(postRequest))

  def post[A](implicit evidence: RequestWithEntity[R],
              system: ActorSystem,
              materializer: Materializer,
              executionContext: ExecutionContext,
              um: Unmarshaller[ResponseEntity, A],
              processor: ResponseProcessor): Future[A] = ClientResponse.as[A](
    Http().singleRequest(postRequest)
  )

  def put()(implicit evidence: RequestWithEntity[R],
            system: ActorSystem,
            materializer: Materializer,
            executionContext: ExecutionContext): Future[ClientResponse] =
    ClientResponse(Http().singleRequest(putRequest))

  def put[A](implicit evidence: RequestWithEntity[R],
             system: ActorSystem,
             materializer: Materializer,
             executionContext: ExecutionContext,
             um: Unmarshaller[ResponseEntity, A],
             processor: ResponseProcessor): Future[A] = ClientResponse.as[A](
    Http().singleRequest(putRequest)
  )

  def patch()(implicit evidence: RequestWithEntity[R],
              system: ActorSystem,
              materializer: Materializer,
              executionContext: ExecutionContext): Future[ClientResponse] =
    ClientResponse(Http().singleRequest(patchRequest))

  def patch[A](implicit evidence: RequestWithEntity[R],
               system: ActorSystem,
               materializer: Materializer,
               executionContext: ExecutionContext,
               um: Unmarshaller[ResponseEntity, A],
               processor: ResponseProcessor): Future[A] = ClientResponse.as[A](
    Http().singleRequest(patchRequest)
  )
}
