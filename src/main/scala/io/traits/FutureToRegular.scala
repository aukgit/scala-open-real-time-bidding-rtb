package io.traits

import scala.concurrent.{Await, Future}
import scala.concurrent.duration.Duration.Inf
import scala.concurrent.duration.Duration

trait FutureToRegular {
  def toRegular[A](request: Future[A], wait: Duration = Inf): A = {
    Await.result(request, Inf)
  }
}
