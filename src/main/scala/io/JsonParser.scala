package io
import io.circe._
import io.circe.generic.auto._
import io.circe.parser._
import io.circe.syntax._
import io.circe.generic.semiauto._

object JsonParser {


  def toObjectFromJSONPath[Type: Decoder](path: String): Option[Type] = {
    try {
      val contents = File.getContents(path)
      if (contents.isEmpty) {
        return null
      }

      val convertedEitherItem = decode[Type](contents);
      val convertedItem = convertedEitherItem.asInstanceOf[Type]

      if (convertedItem != null) {
        return Some(convertedItem)
      }
    }
    catch {
      case e: Exception =>
        AppLogger.error(e)
    }

    null
  }

  def toJSON[Type: Encoder](item: Type): Option[String] = {
    try {
      return Some(item.asJson.toString)
    }
    catch {
      case e: Exception =>
        AppLogger.error(e)
    }

    Some(null);
  }
}
