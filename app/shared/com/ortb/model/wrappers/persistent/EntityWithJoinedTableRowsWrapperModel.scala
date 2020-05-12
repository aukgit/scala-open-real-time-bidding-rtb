package shared.com.ortb.model.wrappers.persistent

import shared.io.helpers.EmptyValidateHelper

case class EntityWithJoinedTableRowsWrapperModel[TBase, TChildRowsType](
  row : Option[TBase],
  innerRows : Option[Seq[TChildRowsType]]
) {
  lazy val hasRow : Boolean = EmptyValidateHelper.isDefined(row)
  lazy val hasInnerRows : Boolean = EmptyValidateHelper.hasAnyItem(innerRows)
}
