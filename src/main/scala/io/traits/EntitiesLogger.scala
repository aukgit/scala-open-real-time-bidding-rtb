package io.traits

import io.traits.logger.InfoLogger

import scala.concurrent.{Future, Await}
import io.AppLogger

import scala.concurrent.duration.Duration._


trait EntitiesLogger {
  this : InfoLogger =>

  def logEntities[T](isExecute : Boolean, f : Future[Iterable[T]], additionalMessage: String = "") : Unit = {
    if (!isExecute) {
      return
    }

    val results = Await.result(f, Inf)
    logEntitiesNonFuture(isExecute, results, additionalMessage)
  }

  def logEntitiesNonFuture[T](isExecute : Boolean, f : Iterable[T], additionalMessage: String = "") : Unit = {
    if (!isExecute) {
      return
    }

    if(additionalMessage.nonEmpty){
      AppLogger.info(additionalMessage)
    }


    if(f.isEmpty){
      AppLogger.info("No item present in the entities for logging.")
    }

    val entityName = f.head.getClass.getTypeName

    AppLogger.info(s"Printing Entities ($entityName):")

    f.foreach(i => {
      AppLogger.info(i.toString)
    })
  }
}
