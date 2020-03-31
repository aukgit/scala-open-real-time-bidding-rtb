package com.ortb

import org.apache.logging.log4j.{LogManager, Logger}

object Logger {
  def methodName(): String = Thread.currentThread().getStackTrace()(3).getMethodName

  val header = "[Open RTB]"
  val log: Logger = LogManager.getRootLogger()

  def info(msg: String = ""): Unit = {
    if (msg.length > 0) {
      log.info(String.format("(%s) - %s", methodName(), msg))
    } else {
      log.info("")
    }
  }

  def debug(msg: String = ""): Unit = {
    if (msg.length > 0) {
      log.debug(String.format("(%s) - %s", methodName(), msg))
    } else {
      log.debug("")
    }
  }

  def warn(msg: String = ""): Unit = {
    if (msg.length > 0) {
      log.warn(String.format("(%s) - %s", methodName(), msg))
    } else {
      log.warn("")
    }
  }

  def error(msg: String = ""): Unit = {
    if (msg.length > 0) {
      log.error(String.format("(%s) - %s", methodName(), msg))
    } else {
      log.error("")
    }
  }

  def title: Any = log.warn(header)
}
