package com.ortb.serverAkka.framework.sampleCodes

import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import com.ortb.constants.AppConstants
import com.ortb.serverAkka.framework.ServerRun
import com.ortb.serverAkka.framework.restClient.softler.context._

import scala.concurrent.Future

class AkkaServerDefinition(endPointPrefixes : String)
  extends AkkaHttpRequest
    with AkkaHttpResponse
    with AkkaHttpResponseHandler
    with AkkaHttpContext
    with ServerRun {
  override def serverRunAt(port : Int) : Unit = {
    Http().bindAndHandleAsync(requestHandler, AppConstants.LocalHost, port)
  }

  def requestHandler : HttpRequest => Future[HttpResponse] = {
    case HttpRequest(HttpMethods.POST, uri @ Uri.Path(s"/api/$endPointPrefixes"), seqHeaders, entity, _) =>
      println("hello POST")
      println(seqHeaders)
      // println(entity.asJson.noSpaces)
      println(entity)
      println("Query")

      println(uri.query())
      Future {
        HttpResponse(
          status = StatusCodes.Accepted,
          entity = HttpEntity(
            ContentTypes.`text/html(UTF-8)`,
            "POST : You Get to Anything"
            ))
      }

    case HttpRequest(HttpMethods.GET, uri @ Uri.Path(s"/api/$endPointPrefixes"), seqHeaders, entity, _) =>
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

    case request : HttpRequest =>
      request.discardEntityBytes()
      Future {
        HttpResponse(status = StatusCodes.NotFound)
      }
  }
}
