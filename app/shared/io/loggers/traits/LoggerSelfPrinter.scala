package shared.io.loggers.traits

import org.slf4j.Logger

import scala.collection.mutable.ArrayBuffer

trait LoggerSelfPrinter {
  protected def printLoggerStatus(logger1 : Logger, loggerVariableName : String) : Unit = {
    val list = ArrayBuffer[String]()
    list += s"Logger Name : ${ logger1.getName } alias as `$loggerVariableName`"
    list += s"Logger toString : ${ logger1.toString }"
    list += s"Is Info Enabled : ${ logger1.isInfoEnabled() }"
    list += s"Is Debug Enabled : ${ logger1.isDebugEnabled() }"
    list += s"Is Warn Enabled : ${ logger1.isWarnEnabled() }"
    list += s"Is Error Enabled : ${ logger1.isErrorEnabled() }"
    list += s"Is Trace Enabled : ${ logger1.isTraceEnabled() }"

    val toString = list.mkString("\n")

    println(toString)
    logger1.info(toString)
  }


  protected def printLoggerStatus(logger1 : play.api.Logger, loggerVariableName : String) : Unit = {
    val list = ArrayBuffer[String]()
    list += s"Logger Name : ${ logger1.logger.getName } alias as `$loggerVariableName`"
    list += s"Logger toString : ${ logger1.toString }"
    list += s"Is Info Enabled : ${ logger1.isInfoEnabled }"
    list += s"Is Debug Enabled : ${ logger1.isDebugEnabled }"
    list += s"Is Warn Enabled : ${ logger1.isWarnEnabled }"
    list += s"Is Error Enabled : ${ logger1.isErrorEnabled }"
    list += s"Is Trace Enabled : ${ logger1.isTraceEnabled }"

    val toString = list.mkString("\n")

    println(toString)
    logger1.info(toString)
  }
}
