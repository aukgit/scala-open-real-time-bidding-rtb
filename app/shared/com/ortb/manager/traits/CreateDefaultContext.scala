package shared.com.ortb.manager.traits

import scala.concurrent.ExecutionContext

trait CreateDefaultContext {
  def createDefaultContext() : ExecutionContext
}