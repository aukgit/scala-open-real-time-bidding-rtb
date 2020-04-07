package io.traits.logger

import com.ortb.constants.AppConstants
import org.apache.logging.log4j.{LogManager, Logger}

trait LoggerProperties {
  val header = "[Open RTB]"
  val log: Logger = LogManager.getLogger(header)
  val newLine: String = AppConstants.NewLineForSentry
}
