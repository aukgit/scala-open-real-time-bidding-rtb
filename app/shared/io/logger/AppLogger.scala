package shared.io.logger

import ch.qos.logback.classic.LoggerContext
import ch.qos.logback.classic.joran.JoranConfigurator
import org.slf4j
import org.slf4j._
import play.api.{Logging, MarkerContext}
import shared.io.traits.logger.{EntitiesLogger, _}

import scala.collection.mutable.ArrayBuffer

object AppLogger extends
  Logging with
  LoggerProperties with
  MethodNameHeaderGetter with
  MultipleStackTracesInfoDisplayGetter with
  PrintStacks with
  ErrorLogger with
  InfoLogger with
  DebugLogger with
  WarnLogger with
  EntitiesLogger with
  SentryLogger with
  AdditionalLogger {
  // val playLogger = new Logger(innerLogger)
  val marker : Marker = MarkerFactory.getMarker("Something")
  implicit val mc : MarkerContext = MarkerContext(marker)
  val log : slf4j.Logger = LoggerFactory.getLogger(this.getClass)
  printLoggerStatus(log, "log")
  printLoggerStatus(logger, "logger")
  configureLogback("logback.xml")

  private def configureLogback(configFilename: String): Unit = {
    val configurator: JoranConfigurator = new JoranConfigurator
    val ctx: LoggerContext = LoggerFactory.getILoggerFactory.asInstanceOf[LoggerContext]
    configurator.setContext(ctx)
    ctx.reset()
    configurator.doConfigure(getClass.getClassLoader.getResource(configFilename))
  }

  def printLoggerStatus(logger1 : Logger, loggerVariableName: String) : Unit = {
    val list = ArrayBuffer[String]()
    list += s"Logger Name : ${logger1.getName} alias as `$loggerVariableName`"
    list += s"Logger toString : ${logger1.toString}"
    list += s"Is Info Enabled : ${logger1.isInfoEnabled()}"
    list += s"Is Debug Enabled : ${logger1.isDebugEnabled()}"
    list += s"Is Warn Enabled : ${logger1.isWarnEnabled()}"
    list += s"Is Error Enabled : ${logger1.isErrorEnabled()}"
    list += s"Is Trace Enabled : ${logger1.isTraceEnabled()}"

    val toString = list.mkString("\n")

    println(toString)
    logger1.info(toString)
  }


  def printLoggerStatus(logger1 : play.api.Logger, loggerVariableName: String) : Unit = {
    val list = ArrayBuffer[String]()
    list += s"Logger Name : ${logger1.logger.getName} alias as `$loggerVariableName`"
    list += s"Logger toString : ${logger1.toString}"
    list += s"Is Info Enabled : ${logger1.isInfoEnabled}"
    list += s"Is Debug Enabled : ${logger1.isDebugEnabled}"
    list += s"Is Warn Enabled : ${logger1.isWarnEnabled}"
    list += s"Is Error Enabled : ${logger1.isErrorEnabled}"
    list += s"Is Trace Enabled : ${logger1.isTraceEnabled}"

    val toString = list.mkString("\n")

    println(toString)
    logger1.info(toString)
  }
}

