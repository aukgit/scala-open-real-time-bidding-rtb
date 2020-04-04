package io

import io.circe._

object JsonParser {
  def toObjectFromJSONPath[Type](
    path: String,
    converter: () => Either[Error, Type]): Option[Type] = {
    try {
      val contents = File.getContents(path)
      if (contents.isEmpty) {
        return null
      }

      val convertedEitherItem = converter()
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
}
