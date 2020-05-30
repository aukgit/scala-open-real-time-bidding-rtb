package shared.com.ortb.serverAkka.framework.restClient.softler.context

import akka.actor.ActorSystem
import akka.stream.{ ActorMaterializer, Materializer }

import scala.concurrent.ExecutionContext

/**
 * @author Freshwood
 * @since 13.08.2018
 */
trait AkkaHttpContext {
  implicit lazy val system : ActorSystem = ActorSystem()
  implicit lazy val materializer : Materializer = ActorMaterializer()
  implicit lazy val executionContext : ExecutionContext = system.dispatcher
}

object AkkaHttpContext extends AkkaHttpContext
