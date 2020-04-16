package com.ortb.serverAkka.framework.restClient.jsonParser.circe

import akka.actor.ActorSystem
import akka.stream.{ActorMaterializer, Materializer}
import com.ortb.serverAkka.framework.restClient.softler.client.ClientRequest

import scala.concurrent.{ExecutionContext, Future}

/**
 * Sample shows how to fetch json information in an very simple fashion
 */
object AkkaHttpClientCirceSample extends App
  with FailFastCirceSupport {

  implicit lazy val system           : ActorSystem      = ActorSystem()
  implicit lazy val materializer     : Materializer     = ActorMaterializer()
  implicit lazy val executionContext : ExecutionContext = system.dispatcher
  val clientRequest : Future[GithubUser] = ClientRequest("https://api.github.com/users/Freshwood")
    .get[GithubUser]

  case class GithubUser(login : String)

  clientRequest.foreach(println)
}
