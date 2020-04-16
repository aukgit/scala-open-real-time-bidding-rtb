package io.traits

import scala.concurrent.duration.Duration
import scala.concurrent.duration.Duration.Inf
import scala.concurrent.{Await, Future}

trait FutureToRegular {
  def toRegular[A](request : Future[A], wait : Duration = Inf) : A = {
    Await.result(request, Inf)
  }
}
