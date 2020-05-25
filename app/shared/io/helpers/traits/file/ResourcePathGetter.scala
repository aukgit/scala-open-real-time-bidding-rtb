package shared.io.helpers.traits.file

import com.github.dwickern.macros.NameOf._
import java.io.File
import java.net.URL
import java.nio.file.FileSystemException

import shared.com.ortb.constants.AppConstants
import shared.com.ortb.model.error.FileErrorModel
import shared.io.loggers.AppLogger

trait ResourcePathGetter {
  //noinspection ScalaDeprecation
  def urlReduceByParent(givenUrl : Option[URL], level : Int) : URL = {
    val methodName = nameOf(urlReduceByParent _)
    if (level <= 0 || givenUrl.isEmpty) {
      return givenUrl.get
    }

    val level2 = level - 1

    try {
      val parentFile = new File(givenUrl.get.getPath).getParentFile
      AppLogger.logMaybeItem(nameOf(parentFile), Some(parentFile))
      return urlReduceByParent(Some(parentFile.toURL), level2)
    }
    catch {
      case e : Exception => AppLogger.fileError(
        FileErrorModel(
          title = s"${methodName}(Level $level -> @$level2)",
          filePath = s"Given : ${ givenUrl.toString }",
          cause = s"Failed at converting to parent folder for level $level -> $level2",
          content = ""))
    }

    null
  }

  def getResourcePath : String = {
    val methodName = nameOf(urlReduceByParent _)

    try {
      var resourceUrl : URL = ClassLoader.getSystemResource("")

      if (resourceUrl == null) {
        resourceUrl = getResourcePathClassDirectory
        AppLogger.logMaybeItem(s"${methodName}, resourceUrl-step1", Some(resourceUrl))
      }

      if (resourceUrl == null) {
        resourceUrl = getResourcePathUsingPlayFramework
        AppLogger.logMaybeItem(s"${methodName}, resourceUrl-step2", Some(resourceUrl))
      }

      if (resourceUrl != null) {
        AppLogger.debug(s"${methodName}, resourceUrl-final", resourceUrl.toString)
        val toPath = resourceUrl.getPath

        return new File(toPath).getAbsolutePath
      }

      throw new FileSystemException(s"${methodName}, Couldn't retrieve system resource path.")
    } catch {
      case e : Exception => AppLogger.fileError(
        FileErrorModel(methodName, "", e.toString, ""))
    }

    ""
  }

  private def getResourcePathUsingPlayFramework : URL = {
    val simpleEnv = play.api.Environment.simple()
    val resourceUrl = simpleEnv.resource(AppConstants.Dot).get
    resourceUrl
  }

  private def getResourcePathClassDirectory : URL = {
    val resourceUrl = this.getClass.getResource("")
    val packages = this.getClass.getPackage.toString.split("\\.")
    urlReduceByParent(Some(resourceUrl), packages.length)
  }
}
