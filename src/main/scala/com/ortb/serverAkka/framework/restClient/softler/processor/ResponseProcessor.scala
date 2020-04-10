package com.ortb.serverAkka.framework.restClient.softler.processor

import akka.http.scaladsl.model.{ResponseEntity, HttpResponse, StatusCodes}
import akka.stream.Materializer
import com.ortb.serverAkka.framework.restClient.softler.exception._

/**
  * The response processor is handling all common [[StatusCodes]]
  * The processor can be overridden with any logic you want
  * In the future there a multiple response processors planed
  * (e.g. some with retry logic and so on...)
  */
trait ResponseProcessor {

  type ResponseHandler = PartialFunction[HttpResponse, ResponseEntity]

  /**
    * Success handler override this when you want to match only a single status
    */
  def success(implicit materializer: Materializer): ResponseHandler

  /**
    * Client error handler (e.g. Bad Request) override this when you want to match only a single status
    */
  def clientError(implicit materializer: Materializer): ResponseHandler

  /**
    * Error handler (e.g. Internal Server error) override this when you want to match only a single status
    */
  def error(implicit materializer: Materializer): ResponseHandler

  /**
    * Informational handler (e.g. Continue...) override this when you want to match only a single status
    */
  def informational(implicit materializer: Materializer): ResponseHandler

  /**
    * Redirect handler (e.g. Redirect...) override this when you want to match your own redirection logic
    */
  def redirect(implicit materializer: Materializer): ResponseHandler

  /**
    * Default response handler override this when you want to match your own custom codes
    */
  def default(implicit materializer: Materializer): ResponseHandler

  /**
    * Processor which runs the handlers from top to bottom
    * The materializer is necessary to discard the underlying response entity (onError)
    * See this: https://doc.akka.io/docs/akka-http/current/scala/http/implications-of-streaming-http-entity.html
    */
  def process(response: HttpResponse)(implicit materializer: Materializer): ResponseEntity
}

object ResponseProcessor {

  /**
    * The default http response processor
    */
  implicit object DefaultProcessor extends ResponseProcessor {

    override def success(implicit materializer: Materializer): ResponseHandler = {
      case HttpResponse(_: StatusCodes.Success, _, entity, _) => entity
    }

    override def clientError(implicit materializer: Materializer): ResponseHandler = {
      case r @ HttpResponse(status: StatusCodes.ClientError, _, entity, _) =>
        r.discardEntityBytes()
        throw ClientErrorRestException(
          s"Client error occurred for status code [${status.intValue}] with response entity [${entity.toString}]"
        )
    }

    override def error(implicit materializer: Materializer): ResponseHandler = {
      case r @ HttpResponse(status: StatusCodes.ServerError, _, entity, _) =>
        r.discardEntityBytes()
        throw ServerErrorRestException(
          s"Server error occurred for status code [${status.intValue}] with response entity [${entity.toString}]"
        )
    }

    override def informational(implicit materializer: Materializer): ResponseHandler = {
      case r @ HttpResponse(status: StatusCodes.Informational, _, entity, _) =>
        r.discardEntityBytes()
        throw InformationalErrorRestException(
          s"Information error occurred for status code [${status.intValue}] with response entity [${entity.toString}]"
        )
    }

    /**
      * Redirect handler (e.g. Redirect...) override this when you want to match your own redirection logic
      */
    override def redirect(implicit materializer: Materializer): ResponseHandler = {
      case r @ HttpResponse(status: StatusCodes.Redirection, _, entity, _) =>
        r.discardEntityBytes()
        throw RedirectionErrorRestException(
          s"Redirection error occurred for status code [${status.intValue}] with response entity [${entity.toString}]"
        )
    }

    /**
      * Custom status code handler override this when you want to match your own custom codes
      */
    override def default(implicit materializer: Materializer): ResponseHandler = {
      case r @ HttpResponse(status, _, entity, _) =>
        r.discardEntityBytes()
        throw CustomRestException(
          s"Unknown response for status code [${status.intValue}] with response entity [${entity.toString}]"
        )
    }

    override def process(response: HttpResponse)(
        implicit materializer: Materializer): ResponseEntity =
      (success orElse clientError orElse error orElse informational orElse redirect orElse default)(
        response)
  }

}
