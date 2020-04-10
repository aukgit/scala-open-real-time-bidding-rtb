package com.ortb.serverAkka.framework.restClient.softler.client

import akka.http.scaladsl.coding._
import akka.http.scaladsl.model._
import akka.http.scaladsl.model.headers.HttpEncodings
import akka.http.scaladsl.unmarshalling.{Unmarshal, Unmarshaller}
import akka.stream.Materializer
import com.ortb.serverAkka.framework.restClient.softler.processor._

import scala.collection.immutable
import scala.concurrent.{Future, ExecutionContext}
import scala.language.implicitConversions

/**
  * The response wrapper which is just a mirror for a [[HttpResponse]]
  * Here you access all relevant response information
  * Or you can process the response with a [[ResponseProcessor]]
  * As default the default [[ResponseProcessor]] will be used
  */
case class ClientResponse(response: HttpResponse) { self =>

  implicit val fallbackProcessor: ResponseProcessor = ResponseProcessor.DefaultProcessor

  private val decoder: HttpResponse => HttpResponse = ClientResponse.decodeResponse

  def protocol: HttpProtocol = response.protocol

  def headers: immutable.Seq[HttpHeader] = response.headers

  def status: StatusCode = response.status

  def raw: ResponseEntity = response.entity

  def decoded: HttpResponse = decoder(response)

  /**
    * Decodes the given response with the well known encoding mechanism
    */
  def decode: ClientResponse = ClientResponse(decoder(self.response))

  /**
    * Process the [[HttpResponse]] with implicit response processor
    * If none is specified the default one will be used
    * @return A response entity which can be un marshaled
    */
  def process(implicit processor: ResponseProcessor, materializer: Materializer): ResponseEntity =
    processor.process(response)

  /**
    * Process the response with the [[ResponseProcessor]] and
    * un marshall the entity with the given un marshaller
    * @return The given type as [[A]]
    */
  def as[A](implicit processor: ResponseProcessor,
            um: Unmarshaller[ResponseEntity, A],
            materializer: Materializer): Future[A] =
    Unmarshal(processor.process(response)).to[A]
}

/**
  * The companion object for easy usage
  */
object ClientResponse {

  /**
    * Applies a future http response to a future [[ClientResponse]]
    */
  def apply(response: Future[HttpResponse])(implicit ex: ExecutionContext): Future[ClientResponse] =
    response map (ClientResponse(_))

  /**
    * Applies a future http response to a future of type [[A]]
    * The response will be processed with the given [[ResponseProcessor]]
    * With the response entity as result the given un marshaller will be applied
    */
  def as[A](response: Future[HttpResponse])(implicit processor: ResponseProcessor,
                                            um: Unmarshaller[ResponseEntity, A],
                                            materializer: Materializer,
                                            executionContext: ExecutionContext): Future[A] =
    response flatMap { rawResult =>
      Unmarshal(processor.process(rawResult)).to[A]
    }

  def decodeResponse(response: HttpResponse): HttpResponse = response.encoding match {
    case HttpEncodings.gzip ⇒
      Gzip.decodeMessage(response)
    case HttpEncodings.deflate ⇒
      Deflate.decodeMessage(response)
    case _ ⇒
      NoCoding.decodeMessage(response)
  }
}
