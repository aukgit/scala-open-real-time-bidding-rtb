package io.traits

import io.traits.logger.InfoLogger

import scala.concurrent.duration.Duration._
import scala.concurrent.{Await, Future}


trait EntitiesLogger {
  this : InfoLogger =>

  def logEntities[T](isExecute : Boolean, f : Future[Iterable[T]], additionalMessage : String = "") : Unit = {
    if (!isExecute) {
      return
    }

    val results = Await.result(f, Inf)
    logEntitiesNonFuture(isExecute, results, additionalMessage)
  }

  def logEntitiesNonFuture[T](isExecute : Boolean, f : Iterable[T], additionalMessage : String = "") : Unit = {
    if (!isExecute) {
      return
    }

    if (additionalMessage.nonEmpty) {
      println(additionalMessage)
    }

    if (f.isEmpty) {
      println("No item present in the entities for logging.")

      return;
    }

    val entityName = f.head.getClass.getTypeName.replace("$", ".")

    println(s"Printing Entities ($entityName):")

    f.foreach(i => {
      println(i.toString)
    })
  }
}
