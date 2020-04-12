package com.ortb.manager

import scala.concurrent.{ExecutionContext, ExecutionContextExecutor}

class ExecutionContextManager {
  lazy val globalExecutionContext: ExecutionContextExecutor = ExecutionContext.global

  def createDefault(initialParallelism: Int = 4): ExecutionContextExecutor =  createNew(initialParallelism)

  def createNew(initialParallelism: Int): ExecutionContextExecutor = {
    ExecutionContext.fromExecutor(
      new java.util.concurrent.ForkJoinPool(initialParallelism: Int)
    )
  }
}
