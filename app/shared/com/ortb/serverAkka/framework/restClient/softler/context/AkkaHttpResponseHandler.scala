package com.ortb.serverAkka.framework.restClient.softler.context

import akka.stream.Materializer
import com.ortb.serverAkka.framework.restClient.softler.client.ClientResponse

import scala.concurrent.{ExecutionContext, Future}

trait AkkaHttpResponseHandler {

  implicit def executionContext : ExecutionContext

  implicit def materializer : Materializer

  def response : Future[ClientResponse]

  response flatMap (_.as[String]) foreach println
}
