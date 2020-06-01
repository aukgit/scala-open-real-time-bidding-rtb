package shared.io.helpers

import shared.com.ortb.constants.AppConstants
import shared.com.ortb.controllers.traits.ConfigBasedLogger
import shared.io.extensions.TypeConvertExtensions._
import shared.io.helpers.traits.file.ResourcePathGetter
import shared.io.loggers.AppLogger

object PathHelper extends ResourcePathGetter {

  lazy val pathSeparator : String = AppConstants.PathConstants.DirectorySeparator
  lazy val genericSeparator : String = AppConstants.PathConstants.GenericPathSeparator
  lazy val resourceDirectory : String = AppConstants.PathConstants.ResourceDirectory
  lazy val configDirectory : String = AppConstants.PathConstants.ConfigDefaultDirectory

  def getCombinedPathWith(separator : String, relativePathInResources : String*) : String = {
    getCombinedPathWithSequence(separator, relativePathInResources)
  }

  /**
   * Get the absolute path from the given relative path to resource folder.
   *
   * @param relativePathInResources (given multiple relative path folders) will be converted to absolute path.
   *
   * @return the absolute path from the given relative path to resource folder. Returns null and logs if got into an
   *         issue.
   */
  def getResourceFileAbsolutePath(relativePathInResources : String*) : String =
    getResourceFileAbsolutePathSequence(relativePathInResources)

  /**
   * Get the absolute path from the given relative path to resource folder.
   *
   * @param relativePathInResources (given multiple relative path folders) will be converted to absolute path.
   *
   * @return the absolute path from the given relative path to resource folder. Returns null and logs if got into an
   *         issue.
   */
  def getResourceFileAbsolutePathSequence(relativePathInResources : Seq[String]) : String = {
    try {
      val relativeCombined = getCombinedPathWithSequence(pathSeparator, relativePathInResources)
      var separator = ""

      if (relativeCombined(0) != pathSeparator.charAt(0)) {
        separator = pathSeparator
      }

      val absolutePath = s"${ AppConstants.PathConstants.ResourceDirectory }${ separator }${ relativeCombined }"
      AppLogger.debug("Absolute Path", absolutePath);
      return absolutePath
    }
    catch {
      case e : Exception => AppLogger.error(e)
    }

    null
  }

  def getCombinedPathWithSequence(separator : String = pathSeparator, relativePathInResources : Seq[String]) : String =
    relativePathInResources.mkString(separator).replace(s"${ separator }${ separator }", separator)

  /**
   * Expand variables to specific paths ${WorkingDirectory},${WDir}, ${WDir} to Working Directory (Root).
   * ${ResourceDirectory},${ResourceDir}, ${RDir} to Resource Directory.
   *
   * @param expressionPath
   *
   * @return
   */
  def getSafeExpendedPath(expressionPath : String) : String = {
    if (expressionPath.isStringEmpty) {
      return ""
    }

    getExpendedPath(expressionPath)
  }

  /**
   * Expand variables to specific paths ${WorkingDirectory},${WDir}, ${WDir} to Working Directory (Root).
   * ${ResourceDirectory},${ResourceDir}, ${RDir} to Resource Directory.
   *
   * @param expressionPath
   *
   * @return
   */
  def getExpendedPath(expressionPath : String) : String = {
    try {
      val expending1 = expressionPath.replace(genericSeparator, pathSeparator)
      val expending2 = expending1.replace("${WorkingDirectory}", AppConstants.PathConstants.WorkingDirectory)
      val expending3 = expending2.replace("${WDir}", AppConstants.PathConstants.WorkingDirectory)
      val expending4 = expending3.replace("${ResourceDirectory}", resourceDirectory)
      val expending5 = expending4.replace("${ResourceDir}", resourceDirectory)
      val expending6 = expending5.replace("${RDir}", resourceDirectory)
      val expending7 = expending6.replace("${ConfigDir}", configDirectory)

      AppLogger.info(s"Expression($expressionPath) Path Expanding($expending7)")

      return expending7
    }
    catch {
      case e : Exception => AppLogger.error(e)
    }

    null
  }
}
