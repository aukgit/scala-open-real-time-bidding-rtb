package com.ortb.serverAkka.framework.sampleCodes

import akka.util.Timeout
import com.ortb.serverAkka.framework.sampleCodes.GuitarDB.CreateGuitar

import scala.concurrent.{duration, Future}
import akka.actor.{ActorSystem, Props}
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpResponse, HttpRequest, StatusCodes, HttpEntity, HttpMethods, Uri, ContentTypes}
import akka.stream.ActorMaterializer
import io.circe.generic.auto._
import io.circe.parser._
import io.circe.syntax._

class SampleServer() {
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
