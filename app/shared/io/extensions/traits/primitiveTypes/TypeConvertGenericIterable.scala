package shared.io.extensions.traits.primitiveTypes

import io.circe.Json
import io.circe.generic.decoding.DerivedDecoder
import io.circe.generic.encoding.DerivedAsObjectEncoder
import shapeless.Lazy
import shared.com.ortb.constants.AppConstants
import shared.com.ortb.enumeration.{ DatabaseActionType, LogLevelType }
import shared.com.ortb.enumeration.LogLevelType.LogLevelType
import shared.com.ortb.manager.AppManager
import shared.com.ortb.persistent.repositories.LogTraceRepository
import shared.com.ortb.persistent.schema.Tables
import shared.io.extensions.TypeConvertExtensions._
import shared.io.helpers.{ EmptyValidateHelper, JodaDateTimeHelper, ReflectionHelper }
import shared.io.jsonParse.implementations.BasicJsonEncoderImplementation
import shared.io.jsonParse.traits.{ BasicJsonEncoder, GenericJsonParser }
import shared.io.loggers.AppLogger

trait TypeConvertGenericIterable[T] {
  protected val anyItems : Iterable[T]

  lazy val isEmpty : Boolean = !hasItem
  lazy val hasItem : Boolean = EmptyValidateHelper.hasAnyItemDirect(anyItems)
  lazy val toSome : Option[Iterable[T]] = Some(anyItems)
  lazy val toOption : Option[Iterable[T]] = Some(anyItems)
  lazy val toMaybe : Option[Iterable[T]] = Some(anyItems)
  lazy val toCsv : String = anyItems.mkString(",")
  lazy val appManager : AppManager = AppConstants.AppManager
  lazy val logTraceRepository : LogTraceRepository = AppConstants.Repositories.logTraceRepository

  def toJoinString(separator : String) : String = anyItems.mkString(separator)

  def toJoinString(start : String, separator : String, end : String) : String =
    anyItems.mkString(start, separator, end)

  def toJsons(
               implicit decoder : Lazy[DerivedDecoder[T]],
               encoder : Lazy[DerivedAsObjectEncoder[T]]) : Option[Iterable[Json]] = {
    throwOnNullOrNoneOrNil()
    jsonGenericParser(decoder, encoder).fromModelsToJsonObjects(toSome)
  }

  def toJsonString(implicit decoder : Lazy[DerivedDecoder[T]],
                   encoder : Lazy[DerivedAsObjectEncoder[T]]) : String = {
    throwOnNullOrNoneOrNil()
    val mayJsonString = jsonGenericParser(decoder, encoder).fromModelsToJsonString(toSome)
    mayJsonString.getDefinedString
  }

  def jsonGenericParser(
                         implicit decoder : Lazy[DerivedDecoder[T]],
                         encoder : Lazy[DerivedAsObjectEncoder[T]]) : GenericJsonParser[T] =
    basicJsonEncoder(decoder, encoder).getJsonGenericParser

  def basicJsonEncoder(
                        implicit decoder : Lazy[DerivedDecoder[T]],
                        encoder : Lazy[DerivedAsObjectEncoder[T]]) : BasicJsonEncoder[T] =
    new BasicJsonEncoderImplementation[T]()(decoder, encoder)

  private def throwOnNullOrNoneOrNil() : Unit = {
    EmptyValidateHelper.throwOnNullOrNoneOrNil(
      anyItems,
      Some(s"Undefined object given for json parsing."))
  }

  def logAsJson(message : String = "")(
    implicit decoder : Lazy[DerivedDecoder[T]],
    encoder : Lazy[DerivedAsObjectEncoder[T]]) : Unit = {
    val json = toPrettyJsonString
    AppLogger.log(json, message)
  }

  def toPrettyJsonString(
                          implicit decoder : Lazy[DerivedDecoder[T]],
                          encoder : Lazy[DerivedAsObjectEncoder[T]]) : String = {
    throwOnNullOrNoneOrNil()
    val maybeJson = jsonGenericParser(decoder, encoder).toJsonStringPrettyFormatForModels(
      toSome)

    maybeJson.getDefinedString
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
    isForceLog: Boolean = false,
    logLevelType: LogLevelType = LogLevelType.DEBUG)(
    implicit decoder : Lazy[DerivedDecoder[T]],
    encoder : Lazy[DerivedAsObjectEncoder[T]]) : Unit = {
    val json = toPrettyJsonString
    val typeName = ReflectionHelper.getTypeName(anyItems.headOption)
    val logTrace = Tables.LogtraceRow(
      AppConstants.NewRecordIntId,
      methodName.toSome,
      typeName.toSome,
      request.toSome,
      message.toSome,
      json.toSome,
      databaseActionType.value.toSome,
      JodaDateTimeHelper.nowUtcJavaInstantOption)

    if (appManager.config.logConfiguration.isPrintDuringLogDatabaseActionsToDatabase) {
      AppLogger.logAsJson(
        message,
        logLevelType = logLevelType,
        maybeModel = logTrace.toSome)
    }

    if (appManager.config.logConfiguration.isLogToDatabaseTrace) {
      logTraceRepository.addAsync(logTrace)
    }
  }
}
