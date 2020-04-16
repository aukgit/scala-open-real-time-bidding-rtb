package io.traits.logger

import com.ortb.constants.AppConstants

trait LoggerProperties {
  val header           = "[Open RTB]"
  val log     : Logger = LogManager.getLogger(header)
  val newLine : String = AppConstants.NewLineForSentry
}
