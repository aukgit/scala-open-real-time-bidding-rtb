package shared.io.traits.file

import java.nio.file.Path

trait ExecuteInputOutputAction {
  def executeInputOutputAction[ReturnType](
    path             : Path,
    performingAction : () => ReturnType,
    emptyResult      : ReturnType
  ) : ReturnType
}
