package shared.com.ortb.manager

import io.circe.generic.semiauto._
import shared.com.ortb.constants.AppConstants
import shared.com.ortb.model.config.ConfigModel
import shared.com.ortb.model.error.FileErrorModel
import shared.io.helpers.PathHelper._
import io.circe._
import io.circe.generic.auto._
import io.circe.parser._
import shared.io.helpers.JsonHelper
import shared.io.loggers.AppLogger

trait ConfigurationManagerType {
  def getConfig(path : String) : ConfigModel
}

class ConfigurationManager extends ConfigurationManagerType {
  def getConfig(path : String) : ConfigModel = {
    try {

      def decoder(jsonContents : String) : Either[Error, ConfigModel] = {
        decode[ConfigModel](jsonContents)
      }

      val result = JsonHelper.toObjectFromJSONPath[ConfigModel](
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

      val returningResult  = result.get
      val databaseGenerate = returningResult.databaseGenerate;

      databaseGenerate.compiledDatabaseUrl = getExpendedPath(databaseGenerate.databaseUrl)
      databaseGenerate.compiledOutputDir = getExpendedPath(databaseGenerate.outputDir)

      return returningResult;
    }
    catch {
      case e : Exception => AppLogger.error(e)
    }

    null
  }
}
