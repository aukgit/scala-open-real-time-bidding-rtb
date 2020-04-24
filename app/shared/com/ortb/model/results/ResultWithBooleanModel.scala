package shared.com.ortb.model.results

case class ResultWithBooleanModel[T](result: Option[T], isSuccess: Boolean)
