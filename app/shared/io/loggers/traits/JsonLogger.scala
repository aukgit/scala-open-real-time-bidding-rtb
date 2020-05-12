package shared.io.loggers.traits

import io.circe.generic.decoding.DerivedDecoder
import io.circe.generic.encoding.DerivedAsObjectEncoder
import shapeless.Lazy
import shared.com.ortb.enumeration.LogLevelType
import shared.com.ortb.enumeration.LogLevelType.LogLevelType
import shared.io.jsonParse.implementations.BasicJsonEncoderImplementation
import shared.io.loggers.AppLogger

trait JsonLogger {
  this : AppLogger.type =>
  def logAsJson[T](
    message : String,
    message2 : String = "",
    maybeModel : Option[T] = None,
    maybeModels : Option[Iterable[T]] = None,
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

        if (maybeModel.isDefined) {
          val logJson = basicJsonEncoder.getJsonGenericParser.toLogStringForEntity(maybeModel)
          additionalLogging(logJson, logLevelType, stackIndex)
        }

        val isExecute = maybeModels.isDefined && maybeModels.get.nonEmpty

        if (isExecute) {
          val logJson = basicJsonEncoder.getJsonGenericParser.toLogStringForEntities(maybeModels)
          additionalLogging(logJson, logLevelType, stackIndex)
        }
      }
      else {
        log(
          "",
          "",
          maybeModel,
          maybeModels,
          isMessagePrint = false,
          logLevelType,
          stackIndex,
          isPrintStack)
      }
    } catch {
      case e : Exception => println(e.toString)
    }
  }
}
