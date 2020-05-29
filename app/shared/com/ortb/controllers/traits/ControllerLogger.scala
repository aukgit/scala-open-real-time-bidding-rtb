package shared.com.ortb.controllers.traits

import shared.io.extensions.TypeConvertExtensions._
import javax.inject.Singleton
import play.api.Logger
import shared.com.ortb.manager.AppManager
import shared.com.ortb.model.config.LogConfigurationModel
import shared.io.loggers.AppLogger

trait ControllerLogger {
  lazy val logConfig : LogConfigurationModel = appManager.config.logConfiguration
  lazy val isPrint : Boolean = logConfig.isDebug ||
    logConfig.isPlayFrameworkActionLogUsingAkkaLogger ||
    logConfig.isPlayFrameworkActionLogUsingAppLogger

  @Singleton
  lazy private val logger : Logger = Logger(this.getClass)
  val appManager : AppManager

  def log(title : String, message : String = "") : Unit = {
    if (!isPrint) {
      return
    }

    lazy val compiledMessage = if (message.hasCharacter)
                                 s"Title : $title, $message"
                               else title

    if (logConfig.isPlayFrameworkActionLogUsingAkkaLogger) {
      logger.debug(compiledMessage)
    }

    if (logConfig.isPlayFrameworkActionLogUsingAppLogger) {
      AppLogger.debug(title, message)
    }
  }
}
