package io

import com.ortb.general.AppConstants

object PathHelper {
  /**
   * Get the absolute path from the given relative path to resource folder.
   * @param relativePathInResources (given multiple relative path folders) will be converted to absolute path.
   * @return the absolute path from the given relative path to resource folder. Returns null and logs if got into an issue.
   */
  def getResourceFileAbsolutePath(relativePathInResources : String*): String = {
    try {
      val separator = AppConstants.PathConstants.DirectorySeparator
      val relativeCombined = relativePathInResources.mkString(separator)

      val absolutePath = s"${AppConstants.PathConstants.ResourcePath}${separator}${relativeCombined}"
      AppLogger.info(absolutePath);

    } catch  {
      case e: Exception => AppLogger.error(e)
    }

    null
  }
}
