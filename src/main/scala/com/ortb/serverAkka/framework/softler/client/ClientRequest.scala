package com.ortb.serverAkka.framework.softler.client

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.model.headers.Accept
import akka.http.scaladsl.unmarshalling.{Unmarshal, Unmarshaller}
import akka.stream.Materializer
import com.ortb.serverAkka.framework.softler.processor.ResponseProcessor

import scala.annotation.implicitNotFound
import scala.collection._
import scala.concurrent.{Future, ExecutionContext}

/**
  * The evidences to build a [[ClientRequest]]
  */
sealed trait RequestState

object RequestState {

  sealed trait Idempotent extends RequestState

  sealed trait NotReady extends RequestState

  sealed trait EntityAcceptance extends RequestState

  @implicitNotFound("The request is not idempotent. you need for example: GET, DELETE ...")
  type RequestIsIdempotent[S] = S =:= RequestState.Idempotent

  @implicitNotFound("Request is already built")
  type RequestNotReady[S] = S =:= RequestState.NotReady

  @implicitNotFound("A HTTP entity is required for this request type")
  type RequestWithEntity[S] = S =:= RequestState.EntityAcceptance
}

/**
  * The akka http request data holder
  */
sealed trait AkkaHttpRequest {
  def request: HttpRequest
}

sealed trait IdempotentMethods[R <: RequestState] extends AkkaHttpRequest {

  import RequestState._

  private def deletingRequest: HttpRequest = request.copy(method = HttpMethods.DELETE)

  private def getRequest: HttpRequest = request.copy(method = HttpMethods.GET)

  private def headRequest: HttpRequest = request.copy(method = HttpMethods.HEAD)

  private def optionRequest: HttpRequest = request.copy(method = HttpMethods.OPTIONS)

  def get()(implicit evidence: RequestIsIdempotent[R],
            system: ActorSystem,
            materializer: Materializer,
            executionContext: ExecutionContext): Future[ClientResponse] = ClientResponse(
    Http().singleRequest(getRequest)
  )

  def get[A](implicit evidence: RequestIsIdempotent[R],
             system: ActorSystem,
             materializer: Materializer,
             executionContext: ExecutionContext,
             um: Unmarshaller[ResponseEntity, A],
             processor: ResponseProcessor): Future[A] = ClientResponse.as[A](
    Http().singleRequest(getRequest)
  )

  def delete()(implicit evidence: RequestIsIdempotent[R],
               system: ActorSystem,
               materializer: Materializer,
               executionContext: ExecutionContext): Future[ClientResponse] = ClientResponse(
    Http().singleRequest(deletingRequest)
  )

  def delete[A](implicit evidence: RequestIsIdempotent[R],
                system: ActorSystem,
                materializer: Materializer,
                executionContext: ExecutionContext,
                um: Unmarshaller[ResponseEntity, A],
                processor: ResponseProcessor): Future[A] = ClientResponse.as[A](
    Http().singleRequest(deletingRequest)
  )

  def head()(implicit evidence: RequestIsIdempotent[R],
             system: ActorSystem,
             materializer: Materializer,
             executionContext: ExecutionContext): Future[ClientResponse] = ClientResponse(
    Http().singleRequest(headRequest)
  )

  def head[A](implicit evidence: RequestIsIdempotent[R],
              system: ActorSystem,
              materializer: Materializer,
              executionContext: ExecutionContext,
              um: Unmarshaller[ResponseEntity, A],
              processor: ResponseProcessor): Future[A] = ClientResponse.as[A](
    Http().singleRequest(headRequest)
  )

  def options()(implicit evidence: RequestIsIdempotent[R],
                system: ActorSystem,
                materializer: Materializer,
                executionContext: ExecutionContext): Future[ClientResponse] = ClientResponse(
    Http().singleRequest(optionRequest)
  )

  def options[A](implicit evidence: RequestIsIdempotent[R],
                 system: ActorSystem,
                 materializer: Materializer,
                 executionContext: ExecutionContext,
                 um: Unmarshaller[ResponseEntity, A],
                 processor: ResponseProcessor): Future[A] = ClientResponse.as[A](
    Http().singleRequest(optionRequest)
  )
}

sealed trait UnsafeMethods[R <: RequestState] extends AkkaHttpRequest {

  import RequestState._

  private def postRequest: HttpRequest = request.copy(method = HttpMethods.POST)

  private def putRequest: HttpRequest = request.copy(method = HttpMethods.PUT)

  private def patchRequest: HttpRequest = request.copy(method = HttpMethods.PATCH)

  def post()(implicit evidence: RequestWithEntity[R],
             system: ActorSystem,
             materializer: Materializer,
             executionContext: ExecutionContext): Future[ClientResponse] =
    ClientResponse(Http().singleRequest(postRequest))

  def post[A](implicit evidence: RequestWithEntity[R],
              system: ActorSystem,
              materializer: Materializer,
              executionContext: ExecutionContext,
              um: Unmarshaller[ResponseEntity, A],
              processor: ResponseProcessor): Future[A] = ClientResponse.as[A](
    Http().singleRequest(postRequest)
  )

  def put()(implicit evidence: RequestWithEntity[R],
            system: ActorSystem,
            materializer: Materializer,
            executionContext: ExecutionContext): Future[ClientResponse] =
    ClientResponse(Http().singleRequest(putRequest))

  def put[A](implicit evidence: RequestWithEntity[R],
             system: ActorSystem,
             materializer: Materializer,
             executionContext: ExecutionContext,
             um: Unmarshaller[ResponseEntity, A],
             processor: ResponseProcessor): Future[A] = ClientResponse.as[A](
    Http().singleRequest(putRequest)
  )

  def patch()(implicit evidence: RequestWithEntity[R],
              system: ActorSystem,
              materializer: Materializer,
              executionContext: ExecutionContext): Future[ClientResponse] =
    ClientResponse(Http().singleRequest(patchRequest))

  def patch[A](implicit evidence: RequestWithEntity[R],
               system: ActorSystem,
               materializer: Materializer,
               executionContext: ExecutionContext,
               um: Unmarshaller[ResponseEntity, A],
               processor: ResponseProcessor): Future[A] = ClientResponse.as[A](
    Http().singleRequest(patchRequest)
  )
}

/**
  * The http methods implementation which can be mixed in
  */
sealed trait Methods[R <: RequestState] extends IdempotentMethods[R] with UnsafeMethods[R]

/**
  * The request accept headers for easy adding accept headers
  */
sealed trait AcceptHeaders[R <: RequestState] extends AkkaHttpRequest {
  def withJson: ClientRequest[RequestState.Idempotent] =
    ClientRequest(request.addHeader(Accept(MediaTypes.`application/json`)))

  def withText: ClientRequest[RequestState.Idempotent] =
    ClientRequest(request.addHeader(Accept(MediaTypes.`text/plain`)))

  def withXml: ClientRequest[RequestState.Idempotent] =
    ClientRequest(request.addHeader(Accept(MediaTypes.`text/xml`)))

  def withBinary: ClientRequest[RequestState.Idempotent] =
    ClientRequest(request.addHeader(Accept(MediaTypes.`application/octet-stream`)))
}

/**
  * The entity support trait specifies requests with data changes
  */
sealed trait EntitySupport[R <: RequestState] extends AkkaHttpRequest {

  import RequestState._

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

/**
  * The client request which can be easily built with the mixed in traits
  *
  * @param request The actual request state which can be fired via Methods trait
  * @tparam R Determines the builder state
  */
case class ClientRequest[R <: RequestState](request: HttpRequest)
    extends Methods[R]
    with AcceptHeaders[R]
    with EntitySupport[R] {

  import RequestState._

  def body[A](implicit um: Unmarshaller[RequestEntity, A], materializer: Materializer): Future[A] =
    Unmarshal(request.entity).to[A]

  def uri(uri: Uri): ClientRequest[RequestState.Idempotent] = ClientRequest(request.copy(uri = uri))

  def http2: ClientRequest[Idempotent] =
    ClientRequest(request.copy(protocol = HttpProtocols.`HTTP/2.0`))

  def headers(headers: immutable.Seq[HttpHeader]): ClientRequest[Idempotent] =
    ClientRequest(request.copy(headers = headers))
}

/**
  * The companion to easily create a [[ClientRequest]] object
  */
object ClientRequest {

  import RequestState._

  def apply(): ClientRequest[NotReady] = ClientRequest(HttpRequest())

  def apply(uri: Uri): ClientRequest[Idempotent] = ClientRequest(HttpRequest(uri = uri))
}
