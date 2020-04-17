package shared.io.helpers

import java.io.File
import java.net.URL
import java.nio.file.FileSystemException

import shared.com.ortb.constants.AppConstants
import shared.com.ortb.model.error.FileErrorModel
import shared.io.logger.AppLogger

object PathHelper {

  val pathSeparator : String = AppConstants.PathConstants.DirectorySeparator
  val genericSeparator : String = AppConstants.PathConstants.GenericPathSeparator

  def getResourcePath : String = {
    try {
      val resourceUrl : URL = ClassLoader.getSystemResource("")

      AppLogger.debug("resourceUrl", resourceUrl.toString)

      var finalUrl : URL = resourceUrl

      if (resourceUrl == null) {
        finalUrl = getClass.getResource("")
        AppLogger.debug("getClass.getResource", Some(finalUrl).getOrElse("").toString)
      }

      if (finalUrl != null) {
        AppLogger.debug("finalUrl", finalUrl.toString)
        val toPath = finalUrl.getPath

        return new File(toPath).getAbsolutePath
      }

      throw new FileSystemException(s"Couldn't retrieve system resource path.")
    } catch {
      case e : Exception => AppLogger.fileError(
        FileErrorModel("ResourcePath", "", e.toString, ""))
    }

    ""
  }

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
      var separator = ""

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
