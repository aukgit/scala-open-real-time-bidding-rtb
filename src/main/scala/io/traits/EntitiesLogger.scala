package io.traits

import io.traits.logger.InfoLogger

import scala.concurrent.duration.Duration
import scala.concurrent.{Future, Await}
import io.AppLogger

import scala.concurrent.duration.Duration._



trait EntitiesLogger {
  this: InfoLogger =>

  def logEntities[T](isExecute: Boolean, f: Future[Iterable[T]]): Unit = {
    if (!isExecute) {
      return
    }

    Await.result(f, Inf).foreach(i => {
      AppLogger.info(i.toString)
      println(i)
    })

    println()
  }
}
