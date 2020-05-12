package shared.com.ortb.model.results

case class ResultWithSuccessModel[T](
  result : Option[T],
  isSuccess : Boolean) extends ResultModel[T]
