package io

import com.ortb.constants.AppConstants

object PathHelper {

  val pathSeparator    : String = AppConstants.PathConstants.DirectorySeparator
  val genericSeparator : String = AppConstants.PathConstants.GenericPathSeparator

  /**
   * Get the absolute path from the given relative path to resource folder.
   *
   * @param relativePathInResources (given multiple relative path folders) will be converted to absolute path.
   *
   * @return the absolute path from the given relative path to resource folder. Returns null and logs if got into an
   *         issue.
   */
  def getResourceFileAbsolutePath(relativePathInResources : String*) : String = {
    try {
      val relativeCombined = relativePathInResources.mkString(pathSeparator)
      var separator        = ""

      if (relativeCombined(0) != pathSeparator.charAt(0)) {
        separator = pathSeparator
      }

      val absolutePath = s"${AppConstants.PathConstants.ResourcePath}${separator}${relativeCombined}"
      AppLogger.info(absolutePath);
    }
    catch {
      case e : Exception => AppLogger.error(e)
    }

    null
  }

  def getExpendedPath(expressionPath : String) : String = {
    try {
      val expending1 = expressionPath.replace(genericSeparator, pathSeparator)
      val expending2 = expending1.replace("${WorkingDirectory}", AppConstants.PathConstants.WorkingDirectory)
      val expending3 = expending2.replace("${WDir}", AppConstants.PathConstants.WorkingDirectory)
      val expending4 = expending3.replace("${ResourceDirectory}", AppConstants.PathConstants.ResourcePath)
      val expending5 = expending4.replace("${ResourceDir}", AppConstants.PathConstants.ResourcePath)
      val expending6 = expending5.replace("${WDir}", AppConstants.PathConstants.ResourcePath)

      AppLogger.info(expending6);
      return expending6
    }
    catch {
      case e : Exception => AppLogger.error(e)
    }

    null
  }
}
