package shared.io.loggers.traits

import shared.com.ortb.constants.AppConstants
import shared.com.ortb.enumeration.LogLevelType
import shared.com.ortb.enumeration.LogLevelType.LogLevelType
import shared.com.ortb.model.reflection.CaseClassInfoModel
import shared.io.helpers.{ EmptyValidateHelper, ReflectionHelper, ToStringHelper }
import shared.io.helpers.ReflectionHelper.getTypeName
import shared.io.loggers.AppLogger

import scala.concurrent.Future

trait EntitiesLogger {
  this : AppLogger.type =>
  lazy val defaultCharactersCapacity = 2500

  def logEntity[T](
    isExecute : Boolean,
    entityInFuture : Future[T],
    additionalMessage : String = "") : Unit = {
    if (!isExecute) {
      return
    }

    entityInFuture.onComplete(row => {
      logEntityNonFuture(isExecute, Some(row.getOrElse(null)), additionalMessage)
    })
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

  def logEventualEntitiesWithCondition[T](
    isExecute : Boolean,
    entitiesInFuture : Future[Iterable[T]],
    additionalMessage : String = "",
    logLevelType : LogLevelType = LogLevelType.DEBUG,
    isPrintStack : Boolean = false) : Unit = {
    if (!isExecute) {
      return
    }

    entitiesInFuture.onComplete(rows => {
      logEntitiesWithCondition(
        isExecute = isExecute,
        entities = rows.getOrElse(null),
        additionalMessage = additionalMessage,
        logLevelType = logLevelType,
        isPrintStack = isPrintStack)
    })
  }

  def getProductFieldHeader(caseInfoModel : Option[CaseClassInfoModel]) : String = {
    if(caseInfoModel.isEmpty || EmptyValidateHelper.isItemsEmptyDirect(List(caseInfoModel.get))){
      return s"${AppConstants.DoubleSpace}0. Unable to extract field Details."
    }

    val headers = caseInfoModel
      .get
      .caseFieldModel
      .map(w => w.toStringField)
    s"${AppConstants.DoubleSpace}0. ${ToStringHelper.join(Some(headers), "|")}${AppConstants.NewLine}"
  }

  def getLogMessageForEntities[T](
    isExecute : Boolean,
    entities : Iterable[T],
    additionalMessage : String = "",
    logLevelType : LogLevelType = LogLevelType.DEBUG) : String = {
    if (!isExecute) {
      return ""
    }

    val additional = if (additionalMessage.isEmpty) "" else s" Additional : ${ additionalMessage }"

    if (entities == null || entities.isEmpty) {
      return s"Empty -> No item present in the entities for logging. $additional"
    }

    val first = entities.head
    val typeName = getTypeName(Some(first))
    // TODO : Do research on StringBuilder vs ArrayBuffer as in C# List performs better than StringBuilder
    //        for recreation of string builder is always costly.
    //        ON simple search I have found that string builder performs better than ArrayBuffer in scala

    val header = s"\n[$logLevelType]: Printing Entities ($typeName):\n"
    var count = 0
    val caseInfoModel = ReflectionHelper.getCaseClassInfoModel(first)
    val productFieldNamesHeader = getProductFieldHeader(caseInfoModel)

    val items = entities.map(i => {
      count += 1
      if (i != null) {
        s"  ${ count }. ${ i.toString }"
      }
      else {
        s"  ${ count }. null"
      }
    }).mkString(AppConstants.NewLine)

    val footer = s"\n[Complete] Total entities printed : [${ count }]"
    val returningResult = s"${header}${productFieldNamesHeader}${items}${footer}"

    returningResult
  }

  def logEntities[T](
    entities : Iterable[T],
    additionalMessage : String = "",
    logLevelType : LogLevelType = LogLevelType.DEBUG,
    isPrintStack : Boolean = false) : Unit = {

    logEntitiesWithCondition(
      isExecute = true,
      entities,
      additionalMessage,
      logLevelType,
      isPrintStack = isPrintStack)
  }

  def logEntitiesWithCondition[T](
    isExecute : Boolean,
    entities : Iterable[T],
    additionalMessage : String = "",
    logLevelType : LogLevelType = LogLevelType.DEBUG,
    isPrintStack : Boolean = false) : Unit = {
    if (!isExecute) {
      return
    }

    val message = getLogMessageForEntities(
      isExecute,
      entities,
      additionalMessage,
      logLevelType)

    additionalLogging(
      message = message,
      logLevelType = logLevelType,
      stackIndex = defaultStackIndex,
      isPrintStack = isPrintStack
    )
  }
}
