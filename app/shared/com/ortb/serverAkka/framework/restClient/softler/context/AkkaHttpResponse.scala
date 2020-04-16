package com.ortb.serverAkka.framework.restClient.softler.context

import akka.actor.ActorSystem
import akka.stream.Materializer
import com.ortb.serverAkka.framework.restClient.softler.client._

import scala.concurrent.{ExecutionContext, Future}

trait AkkaHttpResponse {

  implicit def system : ActorSystem

  implicit def materializer : Materializer

  implicit def executionContext : ExecutionContext

  lazy val response : Future[ClientResponse] = request.get()

  def request : ClientRequest[RequestState.Idempotent]
}
