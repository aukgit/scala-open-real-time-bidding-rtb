package shared.io.loggers.traits

import shared.com.ortb.constants.AppConstants
import shared.com.ortb.manager.traits.DefaultExecutionContextManagerConcreteImplementation

trait LoggerProperties {
  lazy val header = "[Open RTB]"
  lazy val newLine : String = AppConstants.NewLineForSentry
  lazy val defaultStackIndex = 5
  lazy val defaultSecondStackIndex = 4
  lazy val isPrintln = false
  lazy val isPrintMethodName = true
  lazy val isUseSentry = false
  lazy val executionContextManager = new DefaultExecutionContextManagerConcreteImplementation
}
