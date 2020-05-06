package shared.io.loggers.traits

import com.sun.org.apache.xerces.internal.impl.dv.InvalidDatatypeValueException
import io.circe.generic.decoding.DerivedDecoder
import io.circe.generic.encoding.DerivedAsObjectEncoder
import shapeless.Lazy
import shared.com.ortb.enumeration.LogLevelType
import shared.com.ortb.enumeration.LogLevelType.LogLevelType
import shared.io.jsonParse.implementations.BasicJsonEncoderImplementation
import shared.io.loggers.AppLogger

trait AdditionalLogger {
  this : AppLogger.type =>

  def logAsJson[T](
    message : String,
    message2 : String = "",
    nullableObject : Option[T] = None,
    nullableObjects : Option[Iterable[T]] = None,
    logLevelType : LogLevelType = LogLevelType.DEBUG,
    stackIndex : Int = defaultSecondStackIndex,
    isPrintStack : Boolean = false)(
    implicit decoder : Lazy[DerivedDecoder[T]],
    encoder : Lazy[DerivedAsObjectEncoder[T]]
  ) : Unit = {
    try {
      val messageFinal : String = GetCompiledMessage(message, message2, logLevelType, stackIndex)
      additionalLogging(messageFinal, logLevelType, stackIndex, isPrintStack)
      val isJson = true

      if (isJson) {
        lazy val basicJsonEncoder = new BasicJsonEncoderImplementation[T]()(decoder, encoder)

        if (nullableObject.isDefined) {
          val logJson = basicJsonEncoder.getJsonGenericParser.toLogStringForEntity(nullableObject)
          additionalLogging(logJson, logLevelType, stackIndex)
        }

        val isExecute = nullableObjects.isDefined && nullableObjects.get.nonEmpty

        if (isExecute) {
          val logJson = basicJsonEncoder.getJsonGenericParser.toLogStringForEntities(nullableObjects)
          additionalLogging(logJson, logLevelType, stackIndex)
        }
      }
      else {
        log(
          "",
          "",
          nullableObject,
          nullableObjects,
          isMessagePrint = false,
          logLevelType,
          stackIndex,
          isPrintStack)
      }
    } catch {
      case e : Exception => println(e.toString)
    }
  }

  def GetCompiledMessage[T](
    message : String,
    message2 : String,
    logLevelType : LogLevelType,
    stackIndex : Int) : String = {
    val methodNameDisplay = getMethodNameDisplayWrapper(stackIndex)
    val logTypePrint = logLevelType match {
      case LogLevelType.ALL =>
        LogLevelType.ALL.toString
      case LogLevelType.INFO =>
        LogLevelType.INFO.toString
      case LogLevelType.DEBUG =>
        LogLevelType.DEBUG.toString
      case LogLevelType.WARN =>
        LogLevelType.WARN.toString
      case LogLevelType.ERROR =>
        LogLevelType.ERROR.toString
    }

    val messageFinal = s"$logTypePrint :$methodNameDisplay ${ message }${ message2 }"
    messageFinal
  }

  def log[T](
    message : String,
    message2 : String = "",
    nullableObject : Option[Any] = None,
    nullableObjects : Option[Iterable[T]] = None,
    isMessagePrint: Boolean = true,
    logLevelType : LogLevelType = LogLevelType.DEBUG,
    stackIndex : Int = defaultSecondStackIndex,
    isPrintStack : Boolean = false) : Unit = {
    try {
      val messageFinal : String = GetCompiledMessage(message, message2, logLevelType, stackIndex)

      if(isMessagePrint){
        additionalLogging(messageFinal, logLevelType, stackIndex, isPrintStack)
      }

      if (nullableObject.isDefined) {
        logNonFutureNullable(message = messageFinal, nullableObject, logLevelType, stackIndex, isPrintStack = false)
      }

      val isExecute = nullableObjects.isDefined && nullableObjects.get.nonEmpty
      logEntitiesWithCondition(isExecute = isExecute, nullableObjects, messageFinal)
    } catch {
      case e : Exception => println(e.toString)
    }
  }

  private def logBasedOnLevel(
    message : String,
    logLevelType : LogLevelType) : Unit = {
    if (isPrintln) {
      println(message)
    }

    logLevelType match {
      case LogLevelType.ALL | LogLevelType.INFO =>
        logger.info(message)
      case LogLevelType.DEBUG =>
        logger.debug(message)
      case LogLevelType.WARN =>
        logger.warn(message)
      case LogLevelType.ERROR | LogLevelType.FATAL =>
        logger.error(message)
      case _ =>
        throw new InvalidDatatypeValueException("LogType is out of range", Array(logLevelType))
    }
  }

  def additionalLogging(
    message : String,
    logLevelType : LogLevelType,
    stackIndex : Int = defaultSecondStackIndex,
    isPrintStack : Boolean = false) : Unit = {
    try {
      logBasedOnLevel(message, logLevelType)
      logToSentry(message, logLevelType)
      printStacks(isPrintStack, logLevelType)
    } catch {
      case e : Exception => println(e.toString)
    }
  }

  def additionalLoggingException(
    message : String,
    exception : Option[Exception],
    newError : Option[Error],
    logLevelType : LogLevelType = LogLevelType.ERROR,
    stackIndex : Int = defaultSecondStackIndex + 1,
    isPrintStack : Boolean = false) : Unit = {
    try {
      logger.error(message, exception.get)
      logBasedOnLevel(message, logLevelType)
      exceptionLogToSentry(message, exception, logLevelType)
      errorLogToSentry(newError)
      printStacks(isPrintStack)
    } catch {
      case e : Exception => println(e.toString)
    }
  }
}
