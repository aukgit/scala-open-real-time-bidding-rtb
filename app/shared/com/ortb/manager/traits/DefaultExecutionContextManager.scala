package shared.com.ortb.manager.traits

import shared.com.ortb.manager.AppManager

import scala.concurrent.ExecutionContext

trait DefaultExecutionContextManager {
  val appManager : AppManager
  //noinspection ScalaDeprecation
  lazy implicit val executionContext : ExecutionContext =
    appManager.executionContextManager
      .createDefault()
      .prepare()
}
