package com.ortb.serverAkka.framework.restClient.softler.client

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.stream.Materializer
import com.ortb.serverAkka.framework.restClient.softler.processor.ResponseProcessor

import scala.concurrent.{ExecutionContext, Future}

trait IdempotentMethods[R <: RequestState] extends
  AkkaHttpRequest {

  import com.ortb.serverAkka.framework.restClient.softler.client.RequestState._

  def get()(
    implicit evidence : RequestIsIdempotent[R],
    system : ActorSystem,
    materializer : Materializer,
    executionContext : ExecutionContext
  ) : Future[ClientResponse] = {
    ClientResponse(
      Http().singleRequest(getRequest)
      )
  }

  private def getRequest : HttpRequest = request.copy(method = HttpMethods.GET)

  def get[A](
    implicit evidence : RequestIsIdempotent[R],
    system : ActorSystem,
    materializer : Materializer,
    executionContext : ExecutionContext,
    um : Unmarshaller[ResponseEntity, A],
    processor : ResponseProcessor
  ) : Future[A] = {
    ClientResponse.as[A](
      Http().singleRequest(getRequest)
      )
  }

  def delete()(
    implicit evidence : RequestIsIdempotent[R],
    system : ActorSystem,
    materializer : Materializer,
    executionContext : ExecutionContext
  ) : Future[ClientResponse] = {
    ClientResponse(
      Http().singleRequest(deletingRequest)
      )
  }

  private def deletingRequest : HttpRequest = request.copy(method = HttpMethods.DELETE)

  def delete[A](
    implicit evidence : RequestIsIdempotent[R],
    system : ActorSystem,
    materializer : Materializer,
    executionContext : ExecutionContext,
    um : Unmarshaller[ResponseEntity, A],
    processor : ResponseProcessor
  ) : Future[A] = {
    ClientResponse.as[A](
      Http().singleRequest(deletingRequest)
      )
  }

  def head()(
    implicit evidence : RequestIsIdempotent[R],
    system : ActorSystem,
    materializer : Materializer,
    executionContext : ExecutionContext
  ) : Future[ClientResponse] = {
    ClientResponse(
      Http().singleRequest(headRequest)
      )
  }

  def head[A](
    implicit evidence : RequestIsIdempotent[R],
    system : ActorSystem,
    materializer : Materializer,
    executionContext : ExecutionContext,
    um : Unmarshaller[ResponseEntity, A],
    processor : ResponseProcessor
  ) : Future[A] = {
    ClientResponse.as[A](
      Http().singleRequest(headRequest)
      )
  }

  private def headRequest : HttpRequest = request.copy(method = HttpMethods.HEAD)

  def options()(
    implicit evidence : RequestIsIdempotent[R],
    system : ActorSystem,
    materializer : Materializer,
    executionContext : ExecutionContext
  ) : Future[ClientResponse] = {
    ClientResponse(
      Http().singleRequest(optionRequest)
      )
  }

  private def optionRequest : HttpRequest = request.copy(method = HttpMethods.OPTIONS)

  def options[A](
    implicit evidence : RequestIsIdempotent[R],
    system : ActorSystem,
    materializer : Materializer,
    executionContext : ExecutionContext,
    um : Unmarshaller[ResponseEntity, A],
    processor : ResponseProcessor
  ) : Future[A] = {
    ClientResponse.as[A](
      Http().singleRequest(optionRequest)
      )
  }
}
