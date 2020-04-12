package com.ortb.manager

import scala.concurrent.{ExecutionContext, ExecutionContextExecutor}

class ExecutionContextManager(appManager: AppManager) {
  lazy val globalExecutionContext: ExecutionContextExecutor = ExecutionContext.global
  lazy val defaultParallelProcessing: Int = appManager.config.defaultParallelProcessing

  def createDefault(initialParallelism: Int = defaultParallelProcessing): ExecutionContextExecutor = createNew(initialParallelism)

  def createDefaultContext(): ExecutionContext = createDefault().prepare()

  def createNew(initialParallelism: Int): ExecutionContextExecutor = {
    ExecutionContext.fromExecutor(
      new java.util.concurrent.ForkJoinPool(initialParallelism: Int)
    )
  }
}
