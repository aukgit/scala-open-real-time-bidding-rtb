package shared.com.ortb.manager

import javax.inject.Singleton

import scala.concurrent.{ ExecutionContext, ExecutionContextExecutor }

@Singleton
class ExecutionContextManager(appManager : AppManager) {
  lazy val globalExecutionContext : ExecutionContextExecutor = ExecutionContext.global
  lazy val defaultParallelProcessing : Int = appManager.config.defaultParallelProcessing
  lazy val globalContext : ExecutionContext = concurrent.ExecutionContext.Implicits.global

  def createDefaultContext() : ExecutionContext = createDefault().prepare()

  def createDefault(initialParallelism : Int = defaultParallelProcessing) : ExecutionContextExecutor = createNew(
    initialParallelism)

  def createNew(initialParallelism : Int) : ExecutionContextExecutor = {
    ExecutionContext.fromExecutor(
      new java.util.concurrent.ForkJoinPool(initialParallelism : Int)
      )
  }
}
