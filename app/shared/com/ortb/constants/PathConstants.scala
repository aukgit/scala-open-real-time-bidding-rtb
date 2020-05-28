package shared.com.ortb.constants

import java.io.File

import shared.com.ortb.model.error.FileErrorModel
import shared.io.helpers.PathHelper
import shared.io.loggers.AppLogger

sealed class PathConstants {
  lazy val WorkingDirectory : String = new java.io.File(AppConstants.Dot).getCanonicalPath

  lazy val DirectorySeparator : String = File.separator
  lazy val GenericPathSeparator : String = "/"

  lazy val ResourcePath : String = PathHelper.getResourcePath

  AppLogger.debug("ResourcePath", ResourcePath)

  lazy val ConfigDefaultPath : String =
    s"${ResourcePath}${DirectorySeparator}${AppConstants.DefaultConfigFileNameWithExtension}"
}



