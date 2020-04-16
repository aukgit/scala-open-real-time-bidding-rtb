package com.ortb.serverAkka.framework.restClient.jsonParser.spray

import akka.actor.ActorSystem
import akka.stream.{ActorMaterializer, Materializer}
import com.ortb.serverAkka.framework.restClient.softler.client.ClientRequest

import scala.concurrent.{ExecutionContext, Future}

object JsonModel {

  case class GithubUser(login : String)

}

sealed trait JsonSupport extends
  SprayJsonSupport
  with DefaultJsonProtocol {

  import com.ortb.serverAkka.framework.restClient.jsonParser.spray.JsonModel._

  implicit val githubUserFormat : RootJsonFormat[GithubUser] = jsonFormat1(GithubUser)
}

/**
 * Sample shows how to fetch json information in an very simple fashion
 */
object AkkaHttpClientJsonSample extends App
  with JsonSupport {

  import com.ortb.serverAkka.framework.restClient.jsonParser.spray.JsonModel._

  implicit lazy val system           : ActorSystem      = ActorSystem()
  implicit lazy val materializer     : Materializer     = ActorMaterializer()
  implicit lazy val executionContext : ExecutionContext = system.dispatcher

  val clientRequest : Future[GithubUser] = ClientRequest("https://api.github.com/users/Freshwood")
    .get[GithubUser]

  clientRequest.foreach(println)
}
