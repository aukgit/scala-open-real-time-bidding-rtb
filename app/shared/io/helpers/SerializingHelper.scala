package shared.io.helpers

import org.apache.commons.lang3.SerializationUtils
import shared.io.loggers.AppLogger

object SerializingHelper {
  def toBytesArray(value : Any) : Option[Array[Byte]] = {
    try {
      val valueConverted : Array[Byte] = SerializationUtils.serialize(value.isInstanceOf[Serializable])

      return Some(valueConverted)
    }
    catch {
      case e : Exception =>
        AppLogger.errorCaptureAndThrow(e, s"Having issue during toBytesArray->SerializationUtils.serialize")
    }

    None
  }

  def byteArrayToAny(value : Array[Byte]) : Option[Any] = {
    try {
      val valueConverted : Any = SerializationUtils.deserialize(value)

      return Some(valueConverted)
    }
    catch {
      case e : Exception =>
        AppLogger.errorCaptureAndThrow(e, s"Having issue during byteArrayToAny->SerializationUtils.deserialize")
    }

    None
  }

  def byteArrayToObjectAs[T](value : Array[Byte]) : Option[T] = {
    try {
      val valueConverted : Any = SerializationUtils.deserialize(value)
      //noinspection TypeCheckCanBeMatch
      if (valueConverted.isInstanceOf[T]) {
        return Some(valueConverted.asInstanceOf[T])
      }
    }
    catch {
      case e : Exception =>
        AppLogger.errorCaptureAndThrow(e, s"Having issue during byteArrayToObjectAs->SerializationUtils.deserialize")
    }

    None
  }
}
