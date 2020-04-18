package shared.io.traits.file

import java.io.File
import java.net.URL
import java.nio.file.FileSystemException

import shared.com.ortb.constants.AppConstants
import shared.com.ortb.model.error.FileErrorModel
import shared.io.loggers.AppLogger

trait ResourcePathGetter {
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

  //noinspection ScalaDeprecation
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
}
