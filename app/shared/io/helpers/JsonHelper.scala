package shared.io.helpers

import io.circe.parser._
import io.circe.syntax._
import io.circe.{ Decoder, Encoder, Error, Json }
import shared.com.ortb.model.error.FileErrorModel
import shared.io.jsonParse.traits.CirceJsonSupport
import shared.io.loggers.AppLogger

object JsonHelper extends CirceJsonSupport {
  def toJson[T](item : T)(implicit encoder: Encoder[T]) : Option[Json] = {
    try {
      return Some(item.asJson(encoder))
    } catch {
      case e : Exception =>
        AppLogger.error(e)
    }

    None
  }

  def toObjectUsingParser[T](
    jsonContents : String
  ) (implicit decoder:  Decoder[T]): Option[T] = {
    try {
      val convertedEitherItem = decode[T](jsonContents)(decoder)

      return getObjectFromJson(jsonContents, convertedEitherItem)
    } catch {
      case e : Exception =>
        AppLogger.error(e)
    }

    None
  }

  private def getObjectFromJson[T](
    jsonContents                               : String,
    convertedEitherItem                        : Either[Error, T]) : Option[T] = {
    val result = convertedEitherItem.getOrElse(null)

    if (result != null) {
      return Some(result.asInstanceOf[T])
    }

    val errorContext = convertedEitherItem.left.getOrElse(null).toString
    val fileErrorModel = FileErrorModel(
      title = s"Json Parsing Failed for : $jsonContents",
      cause = errorContext,
      filePath = "",
      content = jsonContents)

    val errorMessage = AppLogger.getFileErrorMessage(fileErrorModel)
    throw new Exception(errorMessage)
  }

  def toObject[Type](
    jsonContents : String,
    converter : (String) => Either[Error, Type]
  ) : Option[Type] = {
    try {
      val convertedEitherItem = converter(jsonContents)

      return getObjectFromJson(jsonContents, convertedEitherItem)
    } catch {
      case e : Exception =>
        AppLogger.error(e)
    }

    None
  }

  def toObjectFromJSONPath[Type](
    path      : String,
    converter : (String) => Either[Error, Type]
  ) : Option[Type] = {
    try {
      val jsonContents = FileHelper.getContents(path)
      if (jsonContents.isEmpty) {
        return None
      }

      return toObject(jsonContents, converter)
    } catch {
      case e : Exception =>
        AppLogger.error(e)
    }

    None
  }
}
