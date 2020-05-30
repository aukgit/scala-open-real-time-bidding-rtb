package shared.com.ortb.manager.traits

import shared.com.ortb.constants.AppConstants

import scala.concurrent.ExecutionContext

trait CreateDefaultContext {
  def createDefaultContext() : ExecutionContext

  implicit val defaultExecutionContext : ExecutionContext = createDefaultContext()
}

object CreateDefaultContext extends CreateDefaultContext {
  override def createDefaultContext() : ExecutionContext = AppConstants
    .AppManager
    .executionContextManager
    .createDefaultContext()
}
