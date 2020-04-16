package com.ortb.serverAkka.framework.restClient.softler.client

import akka.http.scaladsl.model._
import akka.http.scaladsl.unmarshalling.{Unmarshal, Unmarshaller}
import akka.stream.Materializer

import scala.collection._
import scala.concurrent.Future

/**
 * The client request which can be easily built with the mixed in traits
 *
 * @param request The actual request state which can be fired via Methods trait
 * @tparam R Determines the builder state
 */
case class ClientRequest[R <: RequestState](request : HttpRequest)
  extends Methods[R]
    with AcceptHeaders[R]
    with EntitySupport[R] {

  import com.ortb.serverAkka.framework.restClient.softler.client.RequestState._

  def body[A](implicit um : Unmarshaller[RequestEntity, A], materializer : Materializer) : Future[A] =
    Unmarshal(request.entity).to[A]

  def uri(uri : Uri) : ClientRequest[RequestState.Idempotent] = ClientRequest(request.copy(uri = uri))

  def http2 : ClientRequest[Idempotent] =
    ClientRequest(request.copy(protocol = HttpProtocols.`HTTP/2.0`))

  def headers(headers : immutable.Seq[HttpHeader]) : ClientRequest[Idempotent] =
    ClientRequest(request.copy(headers = headers))
}

/**
 * The companion to easily create a [[ClientRequest]] object
 */
object ClientRequest {

  import com.ortb.serverAkka.framework.restClient.softler.client.RequestState._

  def apply() : ClientRequest[NotReady] = ClientRequest(HttpRequest())

  def apply(uri : Uri) : ClientRequest[Idempotent] = ClientRequest(HttpRequest(uri = uri))
}
