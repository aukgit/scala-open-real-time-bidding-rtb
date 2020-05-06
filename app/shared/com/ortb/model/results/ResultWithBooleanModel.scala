package shared.com.ortb.model.results

import shared.io.helpers.EmptyValidateHelper

case class ResultWithBooleanModel[T](result: Option[T], isSuccess: Boolean)


