package com.ortb.serverAkka.framework

import akka.actor.ActorSystem
import akka.stream.{Materializer, ActorMaterializer}
import com.ortb.serverAkka.framework.softler.client.{RequestState, ClientRequest, ClientResponse}

import scala.concurrent.{Future, ExecutionContext}

/**
  * @author Freshwood
  * @since 13.08.2018
  */
sealed trait AkkaHttpContext {
  implicit lazy val system: ActorSystem = ActorSystem()
  implicit lazy val materializer: Materializer = ActorMaterializer()
  implicit lazy val executionContext: ExecutionContext = system.dispatcher
}

sealed trait AkkaHttpRequest {
  lazy val request: ClientRequest[RequestState.Idempotent] = ClientRequest(
    "https://github.com/Freshwood/akka-http-rest-client/blob/master/README.md")
}

sealed trait AkkaHttpResponse {

  implicit def system: ActorSystem

  implicit def materializer: Materializer

  implicit def executionContext: ExecutionContext

  def request: ClientRequest[RequestState.Idempotent]

  lazy val response: Future[ClientResponse] = request.get()
}

sealed trait AkkaHttpResponseHandler {

  implicit def executionContext: ExecutionContext

  implicit def materializer: Materializer

  def response: Future[ClientResponse]

  response flatMap (_.as[String]) foreach println
}

/**
  * This sample shows how to fire a single request
  * The project configuration file will be included inside the client library
  */
object AkkaHttpClientSample
    extends App
    with AkkaHttpRequest
    with AkkaHttpResponse
    with AkkaHttpResponseHandler
    with AkkaHttpContext
