package shared.io.loggers.traits

import shared.com.ortb.enumeration.LogLevelType
import shared.com.ortb.enumeration.LogLevelType.LogLevelType
import shared.com.ortb.manager.traits.{ DefaultExecutionContextManager, DefaultExecutionContextManagerConcreteImplementation }
import shared.com.repository.traits.FutureToRegular
import shared.io.helpers.ReflectionHelper.getTypeName
import shared.io.loggers.AppLogger

import scala.concurrent.duration.Duration.Inf
import scala.concurrent.{ Await, Future }

trait EntitiesLogger {
  this : AppLogger.type =>

  def logEntity[T](
    isExecute : Boolean,
    entityInFuture : Future[T],
    additionalMessage : String = "") : Unit = {
    if (!isExecute) {
      return
    }

    Future {
      val row = FutureToRegular.toRegular(entityInFuture)
      logEntityNonFuture(isExecute, Some(row), additionalMessage)
    }(executionContextManager.newExecutionContext)
  }

  def logEntityNonFuture[T](
    isExecute : Boolean,
    entity : Option[T],
    additionalMessage : String = "",
    logLevelType : LogLevelType = LogLevelType.DEBUG,
    isPrintStack : Boolean = false) : Unit = {
    if (!isExecute) {
      return
    }

    val additional = if (additionalMessage.isEmpty) "" else s" Additional : ${ additionalMessage }"

    if (entity == null || entity.isEmpty) {
      warn(s"Empty -> No entity found. ${ additional }")
      return
    }

    val typeName = getTypeName(entity)

    additionalLogging(
      message = s"[$logLevelType]: Entity($typeName): ${ entity.get.toString }, $additional",
      logLevelType = logLevelType,
      stackIndex = defaultStackIndex,
      isPrintStack = isPrintStack
    )
  }

  def logEntities[T](
    isExecute : Boolean,
    entitiesInFuture : Future[Iterable[T]],
    additionalMessage : String = "",
    logLevelType : LogLevelType = LogLevelType.DEBUG,
    isPrintStack : Boolean = false) : Unit = {
    if (!isExecute) {
      return
    }

    Future {
      val rows = FutureToRegular.toRegular(entitiesInFuture)
      logEntitiesNonFuture(
        isExecute = isExecute,
        entities = rows,
        additionalMessage = additionalMessage,
        logLevelType = logLevelType,
        isPrintStack = isPrintStack)
    }(executionContextManager.newExecutionContext)
  }

  def logEntitiesNonFuture[T](
    isExecute : Boolean,
    entities : Iterable[T],
    additionalMessage : String = "",
    logLevelType : LogLevelType = LogLevelType.DEBUG,
    isPrintStack : Boolean = false) : Unit = {
    if (!isExecute) {
      return
    }

    val additional = if (additionalMessage.isEmpty) "" else s" Additional : ${ additionalMessage }"

    if (entities == null || entities.isEmpty) {
      println(s"Empty -> No item present in the entities for logging. $additional")

      return;
    }

    val typeName = getTypeName(Some(entities.head))

    additionalLogging(
      message = s"\n[$logLevelType]: Printing Entities ($typeName):",
      logLevelType = logLevelType,
      stackIndex = defaultStackIndex,
      isPrintStack = isPrintStack
    )

    var count = 0
    entities.foreach(i => {
      count += 1
      if (i != null) {
        additionalLogging(
          message = s"  ${count}. ${i.toString}",
          logLevelType = logLevelType,
          stackIndex = defaultStackIndex,
          isPrintStack = isPrintStack
        )
      }
      else {
        additionalLogging(
          message = s"  ${count}. null",
          logLevelType = logLevelType,
          stackIndex = defaultStackIndex,
          isPrintStack = isPrintStack
        )
      }
    })

    additionalLogging(
      message = s"\n[Complete] Total entities printed : [${count}]",
      logLevelType = logLevelType,
      stackIndex = defaultStackIndex,
      isPrintStack = isPrintStack
    )
  }
}
