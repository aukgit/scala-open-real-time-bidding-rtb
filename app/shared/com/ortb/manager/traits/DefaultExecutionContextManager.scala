package shared.com.ortb.manager.traits

import scala.concurrent.{ ExecutionContext, ExecutionContextExecutor }

trait DefaultExecutionContextManager extends CreateDefaultContext {
  lazy val defaultParallelProcessing : Int = 8

  def createDefaultContext() : ExecutionContext = createDefault().prepare()

  def newExecutionContext : ExecutionContext = createDefault()
    .prepare()

  def createDefault(initialParallelism : Int = defaultParallelProcessing) : ExecutionContextExecutor = createNewExecutionContext(
    initialParallelism)

  def createNewExecutionContext(initialParallelism : Int) : ExecutionContextExecutor = {
    ExecutionContext.fromExecutor(
      new java.util.concurrent.ForkJoinPool(initialParallelism : Int)
    )
  }
}
