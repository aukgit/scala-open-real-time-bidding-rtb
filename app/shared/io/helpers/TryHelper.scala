package shared.io.helpers

import shared.com.ortb.model.results.ResultWithSuccessModel

import scala.util.Try

object TryHelper {
  def TryResult[T](r : => T) : ResultWithSuccessModel[T] = {
    val action = Try(r)

    if (action.isSuccess) {
      val result = action.get
      return ResultWithSuccessModel[T](Some(result), action.isSuccess)
    }

    ResultWithSuccessModel[T](None, action.isSuccess)
  }
}
