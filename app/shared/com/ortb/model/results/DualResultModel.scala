package shared.com.ortb.model.results

import shared.io.helpers.EmptyValidateHelper

case class DualResultModel[T, T2](
  resultOfT : Option[T] = None,
  resultOfT2 : Option[T2] = None) {
  lazy val hasT : Boolean = EmptyValidateHelper.isDefinedAny(resultOfT)
  lazy val hasT2 : Boolean = EmptyValidateHelper.isDefinedAny(resultOfT2)

  lazy val hasAnySuccess : Boolean = hasT || hasT2
}
