package com.ortb.serverAkka.framework.restClient.softler.context

import akka.stream.Materializer
import akka.actor.ActorSystem

import scala.concurrent.{Future, ExecutionContext}
import com.ortb.serverAkka.framework.restClient.softler.client.{RequestState, ClientResponse, ClientRequest}

trait AkkaHttpResponse {

  implicit def system: ActorSystem

  implicit def materializer: Materializer

  implicit def executionContext: ExecutionContext

  def request: ClientRequest[RequestState.Idempotent]

  lazy val response: Future[ClientResponse] = request.get()
}
