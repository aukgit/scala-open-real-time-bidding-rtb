package com.ortb.manager

import com.ortb.constants.AppConstants
import com.ortb.model.config.ConfigModel
import com.ortb.model.error.FileErrorModel
import io.{AppLogger, JsonParser}
import io.circe._
import io.circe.generic.auto._
import io.circe.parser._
import io.circe.syntax._

trait ConfigurationManagerType {
  def getConfig(path: String): ConfigModel
}

class ConfigurationManager extends ConfigurationManagerType {
  def getConfig(path: String): ConfigModel = {
    try {

      def decoder(jsonContents: String): Either[Error, ConfigModel] = {
        return decode[ConfigModel](jsonContents)
      }

      val result = JsonParser.toObjectFromJSONPath[ConfigModel](
        AppConstants.PathConstants.ConfigDefaultPath,
        decoder)


      if (result.isEmpty) {
        val fileError = FileErrorModel(
          title = "Config Model Failed to Read",
          filePath = path,
          cause = "Result came as empty",
          content = ""
        )

        val errorMessage = AppLogger.getFileErrorMessage(fileError)
        throw new Exception(errorMessage)
      }

      return result.get;
    }
    catch {
      case e: Exception => AppLogger.error(e)
    }

    null
  }
}
