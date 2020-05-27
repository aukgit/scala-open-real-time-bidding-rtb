package shared.io.extensions.traits.genericTypes

import io.circe.generic.decoding.DerivedDecoder
import io.circe.generic.encoding.DerivedAsObjectEncoder
import shapeless.Lazy
import shared.com.ortb.constants.AppConstants
import shared.com.ortb.enumeration.LogLevelType.LogLevelType
import shared.com.ortb.enumeration.{ DatabaseActionType, LogLevelType }
import shared.com.ortb.model.config.LogConfigurationModel
import shared.com.ortb.persistent.schema.Tables
import shared.io.extensions.TypeConvertExtensions._
import shared.io.helpers.{ EmptyValidateHelper, JodaDateTimeHelper, ReflectionHelper }
import shared.io.loggers.AppLogger

import scala.concurrent.Future

trait TypeConvertGenericIterableLogToDatabase[T] {
  this : TypeConvertGenericIterable[T] =>
  lazy private val logConfiguration : LogConfigurationModel = appManager.config.logConfiguration

  /**
   *
   * @param methodName
   * @param databaseActionType
   * @param request
   * @param message
   * @param overrideTypeClass
   * @param isForceLog : If true then log regardless of the configuration or else only log if appManager.config.logConfiguration.isLogToDatabaseTrace
   * @param logLevelType
   * @param decoder
   * @param encoder
   */
  def logToDatabaseAsJsonAsync(
    methodName : String,
    databaseActionType : DatabaseActionType = DatabaseActionType.None,
    request : String = "",
    message : String = "",
    overrideTypeClass : String = null,
    isForceLog : Boolean = false,
    logLevelType : LogLevelType = LogLevelType.DEBUG)(
    implicit decoder : Lazy[DerivedDecoder[T]],
    encoder : Lazy[DerivedAsObjectEncoder[T]]) : Unit = {
    Future {
      logToDatabaseAsJson(
        methodName,
        databaseActionType,
        request,
        message, overrideTypeClass,
        isForceLog,
        logLevelType)
    }
  }

  /**
   *
   * @param methodName
   * @param databaseActionType
   * @param request
   * @param message
   * @param isForceLog : If true then log regardless of the configuration or else only log if appManager.config.logConfiguration.isLogToDatabaseTrace
   * @param logLevelType
   * @param decoder
   * @param encoder
   */
  def logToDatabaseAsJson(
    methodName : String,
    databaseActionType : DatabaseActionType = DatabaseActionType.None,
    request : String = "",
    message : String = "",
    overrideTypeClass : String = null,
    isForceLog : Boolean = false,
    logLevelType : LogLevelType = LogLevelType.DEBUG)(
    implicit decoder : Lazy[DerivedDecoder[T]],
    encoder : Lazy[DerivedAsObjectEncoder[T]]) : Unit = {
    val json = toPrettyJsonString

    val typeName = if (EmptyValidateHelper.isEmptyString(overrideTypeClass))
                     ReflectionHelper.getTypeName(anyItems.headOption)
                   else
                     overrideTypeClass

    val logTrace = Tables.LogtraceRow(
      AppConstants.NewRecordIntId,
      methodName.toSome,
      typeName.toSome,
      request.toSome,
      message.toSome,
      json.toSome,
      databaseActionType.value.toSome,
      JodaDateTimeHelper.nowUtcJavaInstant)

    if (logConfiguration.isPrintDuringLogDatabaseActionsToDatabase) {
      AppLogger.logAsJson(
        message,
        logLevelType = logLevelType,
        maybeModel = logTrace.toSome)
    }

    if (logConfiguration.isLogToDatabaseTrace || isForceLog) {
      logTraceRepository.add(logTrace)
    }
  }
}
