package io

import com.ortb.general.AppConstants

object PathHelper {
  def getResourceFileAbsolutePath(relativePathInResources : String*): String = {
    try {
      val separator = AppConstants.PathConstants.DirectorySeparator
      val relativeCombined = relativePathInResources.mkString(separator)

      return s"${AppConstants.PathConstants.ResourcePath}${separator}${relativeCombined}"
    } catch  {
      case e: Exception => AppLogger.error(e)
    }

    null
  }
}
