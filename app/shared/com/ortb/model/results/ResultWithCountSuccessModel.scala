package shared.com.ortb.model.results

case class ResultWithCountSuccessModel[T](
  result : Option[T],
  count : Int,
  isSuccess : Boolean,
  message : String = "") extends ResultModel[T](result)
