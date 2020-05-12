package shared.com.ortb.model.results

abstract class ResultModel[T] {
  val result : Option[T]
}