package com.ortb.serverAkka.framework.rest.softler.client

import akka.http.scaladsl.model.headers.Accept
import akka.http.scaladsl.model.{MediaRange, ContentTypes, MediaTypes, RequestEntity}

/**
  * The entity support trait specifies requests with data changes
  */
trait EntitySupport[R <: RequestState] extends AkkaHttpRequest {

  import com.ortb.serverAkka.framework.rest.softler.client.RequestState._

  def entity(entity: RequestEntity): ClientRequest[RequestState.EntityAcceptance] =
    ClientRequest(request.copy(entity = entity))

  def entity(data: String): ClientRequest[RequestState.EntityAcceptance] =
    ClientRequest(request.copy(entity = data))

  /**
    * Set the content type as [[ContentTypes.`application/json`]]
    * Make sure we have an entity first otherwise this header gets overwritten
    */
  def asJson(implicit ev: RequestWithEntity[R]): ClientRequest[RequestState.EntityAcceptance] =
    ClientRequest {
      request
        .copy(entity = request.entity.withContentType(ContentTypes.`application/json`))
        .addHeader(Accept(MediaRange(MediaTypes.`application/json`)))
    }

  /**
    * Set the content type as [[ContentTypes.`text/plain(UTF-8)`]]
    */
  def asText(implicit ev: RequestWithEntity[R]): ClientRequest[RequestState.EntityAcceptance] =
    ClientRequest {
      request
        .copy(entity = request.entity.withContentType(ContentTypes.`text/plain(UTF-8)`))
        .addHeader(Accept(MediaRange(MediaTypes.`text/plain`)))
    }

  /**
    * Set the content type as [[ContentTypes.`text/xml(UTF-8)`]]
    */
  def asXml(implicit ev: RequestWithEntity[R]): ClientRequest[RequestState.EntityAcceptance] =
    ClientRequest {
      request
        .copy(entity = request.entity.withContentType(ContentTypes.`text/xml(UTF-8)`))
        .addHeader(Accept(MediaRange(MediaTypes.`text/xml`)))
    }

  /**
    * Set the content type as [[ContentTypes.`application/octet-stream`]]
    */
  def asBinary(implicit ev: RequestWithEntity[R]): ClientRequest[RequestState.EntityAcceptance] =
    ClientRequest {
      request
        .copy(entity = request.entity.withContentType(ContentTypes.`application/octet-stream`))
        .addHeader(Accept(MediaRange(MediaTypes.`application/octet-stream`)))
    }
}
