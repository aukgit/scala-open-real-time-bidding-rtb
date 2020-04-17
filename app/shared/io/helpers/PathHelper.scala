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

  private def getResourcePathUsingPlayFramework : URL = {
    val simpleEnv = play.api.Environment.simple()
    val resourceUrl = simpleEnv.resource(AppConstants.Dot).get
    resourceUrl
  }

  private def getResourcePathClassDirectory : URL = {
    val resourceUrl =this.getClass.getResource("")
    val packages = this.getClass.getPackage.toString.split("\\.")
    urlReduceByParent(Some(resourceUrl), packages.length)
  }

  def urlReduceByParent(givenUrl : Option[URL], level : Int) : URL = {
    if (level <= 0 || givenUrl.isEmpty) {
      return givenUrl.get
    }

    val level2 = level - 1

    try {
      val xFile = new File(givenUrl.get.getPath).getParentFile
      AppLogger.logNonFutureNullable("xFile", Some(xFile))
      return urlReduceByParent(Some(xFile.toURL), level2)
    }
    catch {
      case e : Exception => AppLogger.fileError(
        FileErrorModel(
          title = s"urlReduceByParent(Level $level -> @$level2)",
          filePath = s"Given : ${givenUrl.toString}",
          cause = s"Failed at converting to parent folder for level $level -> $level2",
          content = ""))
    }

    null
  }

  def getResourcePath : String = {
    try {
      println(this.getClass.getPackage.toString)
      println(this.getClass.getPackage.toString.split(AppConstants.Dot).length)
      var resourceUrl : URL = ClassLoader.getSystemResource("")

      if (resourceUrl == null) {
        resourceUrl = getResourcePathClassDirectory
        AppLogger.logNonFutureNullable("resourceUrl-step1", Some(resourceUrl))
      }

      if (resourceUrl == null) {
        resourceUrl = getResourcePathUsingPlayFramework
        AppLogger.logNonFutureNullable("resourceUrl-step2", Some(resourceUrl))
      }

      if (resourceUrl != null) {
        AppLogger.debug("resourceUrl-final", resourceUrl.toString)
        val toPath = resourceUrl.getPath

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
