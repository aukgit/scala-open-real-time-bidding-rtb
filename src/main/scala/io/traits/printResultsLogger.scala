package io.traits

import io.traits.logger.InfoLogger

import scala.concurrent.duration.Duration
import scala.concurrent.{Future, Await}
import io.AppLogger

trait printResultsLogger {
  this: InfoLogger =>

  def printResults[T](f: Future[Iterable[T]]): Unit = {
    Await.result(f, Duration.Inf).foreach(i => {
      AppLogger.info(i.toString)
      println(i)
    })

    println()
  }
}
