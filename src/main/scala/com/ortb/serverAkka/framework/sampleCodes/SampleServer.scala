package com.ortb.serverAkka.framework.sampleCodes

import com.ortb.serverAkka.framework.sampleCodes.GuitarDB.CreateGuitar

import scala.concurrent.Future
import akka.actor.{ActorSystem, Props}
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.stream.ActorMaterializer
import io.circe.{Encoder, Decoder}
import shapeless.Unwrapped

trait AnyValCirceEncoding {
  implicit def anyValEncoder[V, U](implicit ev: V <:< AnyVal,
    V: Unwrapped.Aux[V, U],
    encoder: Encoder[U]): Encoder[V] = {
    val _ = ev
    encoder.contramap(V.unwrap)
  }

  implicit def anyValDecoder[V, U](implicit ev: V <:< AnyVal,
    V: Unwrapped.Aux[V, U],
    decoder: Decoder[U]): Decoder[V] = {
    val _ = ev
    decoder.map(V.wrap)
  }
}

object AnyValCirceEncoding extends AnyValCirceEncoding

object CirceSupport
  extends de.heikoseeberger.akkahttpcirce.FailFastCirceSupport
    with AnyValCirceEncoding

class SampleServer() extends AnyValCirceEncoding{
  def startServerAt(port: Int): Future[Http.ServerBinding] = {
    implicit val system: ActorSystem = ActorSystem("SampleServer")
    implicit val materializer: ActorMaterializer = ActorMaterializer()
    import system.dispatcher
    /*
      setup
     */
    val guitarDb = system.actorOf(Props[GuitarDB], "LowLevelGuitarDB")
    val guitarList = List(
      Guitar("Fender", "Stratocaster"),
      Guitar("Gibson", "Les Paul"),
      Guitar("Martin", "LX1")
    )

    guitarList.foreach { guitar =>
      guitarDb ! CreateGuitar(guitar)
    }

    // implicit val defaultTimeout: Timeout = Timeout(2 seconds)

    def requestHandler: HttpRequest => Future[HttpResponse] = {
      case HttpRequest(HttpMethods.POST, uri@Uri.Path("/api/anything"), seqHeaders, entity, _) =>
        println("hello POST")
        println(seqHeaders)
        // println(entity.asJson.noSpaces)
        println("Query")
        println(uri.query())

        Future {
          HttpResponse(
            status = StatusCodes.Accepted,
            entity = HttpEntity(
              ContentTypes.`text/html(UTF-8)`,
              "You Get to Anything"
            ))
        }

      case HttpRequest(HttpMethods.GET, uri@Uri.Path("/api/anything"), seqHeaders, entity, _) =>
        println("hello GET")
        println(seqHeaders)
        println(entity)
        println("Query")
        println(uri.query())

        Future {
          HttpResponse(
            status = StatusCodes.Accepted,
            entity = HttpEntity(
              ContentTypes.`text/html(UTF-8)`,
              "You Get to Anything"
            ))
        }

      case request: HttpRequest =>
        request.discardEntityBytes()
        Future {
          HttpResponse(status = StatusCodes.NotFound)
        }
    }

    Http().bindAndHandleAsync(requestHandler, "localhost", port)
  }
}
