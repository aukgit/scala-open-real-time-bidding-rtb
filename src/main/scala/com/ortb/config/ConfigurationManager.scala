package com.ortb.config

import com.ortb.general.AppConstants
import com.ortb.model.config.ConfigModel
import com.ortb.model.error.FileErrorModel
import io.{AppLogger, JsonParser}
import io.circe._
import io.circe.generic.auto._
import io.circe.parser._
import io.circe.syntax._

trait GetConfig {
  def getConfig(path: String): ConfigModel
}

object ConfigurationManager extends GetConfig {
  def getConfig(path: String): ConfigModel = {
    try {

      def decoder(jsonContents: String) = {
        decode[ConfigModel](jsonContents)
      }

      val result = JsonParser.toObjectFromJSONPath[ConfigModel](
        AppConstants.PathConstants.ConfigDefaultPath,
        decoder)


      if (result.isEmpty) {
        val fileError = new FileErrorModel(
          title = "Config Model Failed to Read",
          filePath = path,
          cause = "Result came as empty",
          content = ""
        )

        val errorMessage = AppLogger.getFileErrorMessage(fileError)
        throw new Exception(errorMessage)
      }

      println(result.get)

      return result.get;
    }
    catch {
      case e: Exception => AppLogger.error(e)
    }

    null
  }
}
