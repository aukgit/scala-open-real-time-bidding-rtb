package shared.com.ortb.constants

import java.io.File

import shared.com.ortb.model.error.FileErrorModel
import shared.io.logger.AppLogger

sealed class PathConstants {
  lazy val WorkingDirectory : String = new java.io.File(AppConstants.Dot).getCanonicalPath

  lazy val DirectorySeparator : String = File.separator
  lazy val GenericPathSeparator : String = "*:"

  def ResourcePath : String = {
    try {
      val resourceUrl = ClassLoader.getSystemResource(AppConstants.Dot)

      if (resourceUrl != null) {
        val resourcePath = resourceUrl.getPath
        return new File(resourcePath).getAbsolutePath.toString
      }
    } catch {
      case e : Exception => AppLogger.fileError(
        FileErrorModel("ResourcePath", "", e.toString, ""))
    }

    ""
  }

  lazy val ConfigDefaultPath : String =
    s"${ResourcePath}${DirectorySeparator}${AppConstants.DefaultConfigFileNameWithExtension}"
}
