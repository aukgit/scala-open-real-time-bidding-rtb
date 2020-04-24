package shared.io.helpers

import shared.com.ortb.model.results.ResultWithBooleanModel

import scala.util.Try

object TryHelper {
  def TryResult[T](r : => T) : ResultWithBooleanModel[T] = {
    val action = Try(r)

    if(action.isSuccess){
      val result = action.get
      return ResultWithBooleanModel[T](Some(result), action.isSuccess)
    }

    ResultWithBooleanModel[T](None, action.isSuccess)
  }
}
