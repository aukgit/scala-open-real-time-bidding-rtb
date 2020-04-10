package com.ortb.serverAkka.framework.sampleCodes

import com.ortb.constants.AppConstants
import akka.http.scaladsl.model._
import com.ortb.serverAkka.framework.restClient.softler.context.{AkkaHttpResponse, AkkaHttpResponseHandler, AkkaHttpContext, AkkaHttpRequest}

import scala.concurrent.Future
import akka.http.scaladsl.Http
import com.ortb.serverAkka.framework.ServerRun
import com.ortb.serverAkka.framework.restClient.softler.client.ClientResponse

class AkkaServerDefinition(endPointPrefixes: String)
  extends AkkaHttpRequest
    with AkkaHttpResponse
    with AkkaHttpResponseHandler
    with AkkaHttpContext
    with ServerRun {
  def requestHandler: HttpRequest => Future[HttpResponse] = {
    case HttpRequest(HttpMethods.POST, uri@Uri.Path(s"/api/$endPointPrefixes"), seqHeaders, entity, _) =>
      println("hello POST")
      println(seqHeaders)
      // println(entity.asJson.noSpaces)
      println(entity)
      println("Query")
      println(uri.query())

      return Future(ClientResponse())

    case HttpRequest(HttpMethods.GET, uri@Uri.Path(s"/api/$endPointPrefixes"), seqHeaders, entity, _) =>
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

  override def serverRunAt(port: Int): Unit = {
    Http().bindAndHandleAsync(requestHandler, AppConstants.LocalHost, port)
  }
}
