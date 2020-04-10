package com.ortb.serverAkka.framework.rest.softler.context

import akka.actor.ActorSystem

import scala.concurrent.ExecutionContext
import akka.stream.{Materializer, ActorMaterializer}

/**
  * @author Freshwood
  * @since 13.08.2018
  */
trait AkkaHttpContext {
  implicit lazy val system: ActorSystem = ActorSystem()
  implicit lazy val materializer: Materializer = ActorMaterializer()
  implicit lazy val executionContext: ExecutionContext = system.dispatcher
}
