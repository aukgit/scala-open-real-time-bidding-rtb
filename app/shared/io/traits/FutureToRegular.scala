package shared.io.traits

import shared.io.logger.AppLogger

import scala.concurrent.duration.Duration
import scala.concurrent.duration.Duration.Inf
import scala.concurrent.{Await, Future}

trait FutureToRegular {
  def toRegular[A](request : Future[A], wait : Duration = Inf) : A = {
    try {
      return Await.result(request, wait)
    } catch {
      case e : Exception => AppLogger.error(
        e,
        additionalMessage = s"Failed during conversion from future to regular.")
    }

    null.asInstanceOf[A]
  }

  def toRegularOfOption[A](request : Future[Option[A]], wait : Duration = Inf) : Option[A] = {
    try {
      return Await.result(request, wait)
    } catch {
      case e : Exception => AppLogger.error(
        exception = e,
        additionalMessage = s"Failed during conversion from future to regular.")
    }

    None
  }

  def toRegularAsOption[A](request : Future[A], wait : Duration = Inf) : Option[A] = {
    try {
      return Some(Await.result(request, wait))
    } catch {
      case e : Exception => AppLogger.error(
        exception = e,
        additionalMessage = s"Failed during conversion from future to regular.")
    }

    None
  }
}

object FutureToRegular extends FutureToRegular {

}
