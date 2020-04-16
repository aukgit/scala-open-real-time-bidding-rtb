package com.ortb.serverAkka.framework.restClient.softler.client

import akka.http.scaladsl.model.headers.Accept
import akka.http.scaladsl.model.MediaTypes

/**
 * The request accept headers for easy adding accept headers
 */
trait AcceptHeaders[R <: RequestState] extends
  AkkaHttpRequest {
  def withJson : ClientRequest[RequestState.Idempotent] =
    ClientRequest(request.addHeader(Accept(MediaTypes.`application/json`)))

  def withText : ClientRequest[RequestState.Idempotent] =
    ClientRequest(request.addHeader(Accept(MediaTypes.`text/plain`)))

  def withXml : ClientRequest[RequestState.Idempotent] =
    ClientRequest(request.addHeader(Accept(MediaTypes.`text/xml`)))

  def withBinary : ClientRequest[RequestState.Idempotent] =
    ClientRequest(request.addHeader(Accept(MediaTypes.`application/octet-stream`)))
}
