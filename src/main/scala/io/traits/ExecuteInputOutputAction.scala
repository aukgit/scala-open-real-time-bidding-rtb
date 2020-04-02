package io.traits

trait ExecuteInputOutputAction {
  def executeInputOutputAction[ReturnType](
    path: String,
    performingAction: () => ReturnType,
    emptyResult: ReturnType
  ): ReturnType
}
