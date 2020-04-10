package com.ortb.serverAkka.framework.restClient.softler.context

import akka.stream.Materializer

import scala.concurrent.{Future, ExecutionContext}
import com.ortb.serverAkka.framework.restClient.softler.client.ClientResponse

trait AkkaHttpResponseHandler {

  implicit def executionContext: ExecutionContext

  implicit def materializer: Materializer

  def response: Future[ClientResponse]

  response flatMap (_.as[String]) foreach println
}
