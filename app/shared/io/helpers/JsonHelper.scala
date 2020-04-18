package shared.io.helpers
import io.circe.generic.semiauto._
import io.circe._
import io.circe.generic.auto._
import io.circe.parser._
import io.circe.generic.auto._
import io.circe.syntax._
import io.circe.Decoder.AccumulatingResult
import io.circe.generic.JsonCodec
import io.circe.{Error, Json}
import io.circe.parser._
import shared.com.ortb.model.error.FileErrorModel
import shared.io.implementations.JsonParserImplementation
import shared.io.loggers.AppLogger

object JsonHelper {
  def toJson[Type](item: Type) : Option[Json] = {
    try {
      val parser = new JsonParserImplementation[Type]
      val convertedEitherItem =item.asJson(parser.defaultEncoder)
      return getObjectFromJson(jsonContents, convertedEitherItem)
    } catch {
      case e : Exception =>
        AppLogger.error(e)
    }

    None
  }
  def toObjectUsingParser[Type](
    jsonContents : String
  ) : Option[Type] = {
    try {
      val parser = new JsonParserImplementation[Type]
      val convertedEitherItem = decode[Type](jsonContents)(parser.defaultDecoder)

      return getObjectFromJson(jsonContents, convertedEitherItem)
    } catch {
      case e : Exception =>
        AppLogger.error(e)
    }

    None
  }

  private def getObjectFromJson[Type](
    jsonContents : String, convertedEitherItem : Either[Error, Type]) : Option[Nothing] = {
    val result = convertedEitherItem.getOrElse(null)

    if (result != null) {
      return Some(Some(result.asInstanceOf[Type]))
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
