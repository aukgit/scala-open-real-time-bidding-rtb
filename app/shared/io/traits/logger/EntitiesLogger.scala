package shared.io.traits.logger

import shared.com.ortb.enumeration.LogLevelType
import shared.com.ortb.enumeration.LogLevelType.LogLevelType
import shared.io.helpers.ReflectionHelper.getTypeName
import shared.io.logger.AppLogger

import scala.concurrent.duration.Duration.Inf
import scala.concurrent.{Await, Future}

trait EntitiesLogger {
  this : AppLogger.type =>

  def logEntity[T](
    isExecute : Boolean,
    entityInFuture : Future[T],
    additionalMessage : String = "") : Unit = {
    if (!isExecute) {
      return
    }

    val result = Await.result(entityInFuture, Inf)
    logEntityNonFuture(isExecute, Some(result), additionalMessage)
  }

  def logEntityNonFuture[T](
    isExecute         : Boolean,
    entity : Option[T],
    additionalMessage : String = "",
    logLevelType : LogLevelType = LogLevelType.DEBUG,
    isPrintStack      : Boolean = false) : Unit = {
    if (!isExecute) {
      return
    }

    val additional = if (additionalMessage.isEmpty) "" else s" Additional : ${additionalMessage}"

    if (entity.isEmpty) {
      warn(s"Empty -> No entity found. ${additional}")
      return
    }

    val typeName = getTypeName(entity)

    additionalLogging(
      message = s"Entity($typeName): ${entity.get.toString}, $additional",
      logLevelType = logLevelType,
      stackIndex = defaultStackIndex,
      isPrintStack = isPrintStack
    )
  }

  def logEntities[T](
    isExecute : Boolean,
    entitiesInFuture  : Future[Iterable[T]],
    additionalMessage : String = "",
    logLevelType      : LogLevelType = LogLevelType.DEBUG,
    isPrintStack : Boolean = false) : Unit = {
    if (!isExecute) {
      return
    }

    val results = Await.result(entitiesInFuture, Inf)

    logEntitiesNonFuture(
      isExecute = isExecute,
      entities = results,
      additionalMessage = additionalMessage,
      logLevelType = logLevelType,
      isPrintStack = isPrintStack)
  }

  def logEntitiesNonFuture[T](
    isExecute         : Boolean,
    entities          : Iterable[T],
    additionalMessage : String = "",
    logLevelType      : LogLevelType = LogLevelType.DEBUG,
    isPrintStack      : Boolean = false) : Unit = {
    if (!isExecute) {
      return
    }

    val additional = if (additionalMessage.isEmpty) "" else s" Additional : ${additionalMessage}"

    if (entities.isEmpty) {
      println(s"Empty -> No item present in the entities for logging. $additional")

      return;
    }

    val typeName = getTypeName(Some(entities.head))

    additionalLogging(
      message = s"Printing Entities ($typeName):",
      logLevelType = logLevelType,
      stackIndex = defaultStackIndex,
      isPrintStack = isPrintStack
    )

    entities.foreach(i => {
      additionalLogging(
        message = i.toString,
        logLevelType = logLevelType,
        stackIndex = defaultStackIndex,
        isPrintStack = isPrintStack
      )
    })
  }
}
