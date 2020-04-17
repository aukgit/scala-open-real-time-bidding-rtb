package shared.io.traits

import shared.io.logger.AppLogger

import scala.concurrent.{Await, Future}
import scala.concurrent.duration.Duration.Inf
import scala.concurrent.duration.Duration

trait FutureToRegular {
  def toRegular[A](request : Future[A], wait : Duration = Inf) : A = {
    try {
      Await.result(request, wait)
    } catch {
      case e:Exception => AppLogger.error(e)
    }

    null.asInstanceOf[A]
  }
}
