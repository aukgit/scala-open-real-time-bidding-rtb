package com.ortb.serverAkka.framework.restClient.jsonParser.spray

import akka.actor.ActorSystem
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.stream.{Materializer, ActorMaterializer}
import com.ortb.serverAkka.framework.restClient.softler.client.ClientRequest
import spray.json.{RootJsonFormat, DefaultJsonProtocol}

import scala.concurrent.{Future, ExecutionContext}

object JsonModel {
  case class GithubUser(login: String)
}

sealed trait JsonSupport extends SprayJsonSupport with DefaultJsonProtocol {
  import JsonModel._
  implicit val githubUserFormat: RootJsonFormat[GithubUser] = jsonFormat1(GithubUser)
}

/**
  * Sample shows how to fetch json information in an very simple fashion
  */
object AkkaHttpClientJsonSample extends App with JsonSupport {

  import JsonModel._

  implicit lazy val system: ActorSystem = ActorSystem()
  implicit lazy val materializer: Materializer = ActorMaterializer()
  implicit lazy val executionContext: ExecutionContext = system.dispatcher

  val clientRequest: Future[GithubUser] = ClientRequest("https://api.github.com/users/Freshwood")
    .get[GithubUser]

  clientRequest.foreach(println)
}
