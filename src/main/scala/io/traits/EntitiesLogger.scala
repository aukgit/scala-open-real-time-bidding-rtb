package io.traits

import io.traits.logger.InfoLogger

import scala.concurrent.duration.Duration
import scala.concurrent.{Future, Await}
import io.AppLogger

trait EntitiesLogger {
  this: InfoLogger =>

  def logEntities[T](isExecute: Boolean, f: Future[Iterable[T]]): Unit = {
    if(!isExecute) {
      return
    }

    Await.result(f, Duration.Inf).foreach(i => {
      AppLogger.info(i.toString)
      println(i)
    })

    println()
  }
}
