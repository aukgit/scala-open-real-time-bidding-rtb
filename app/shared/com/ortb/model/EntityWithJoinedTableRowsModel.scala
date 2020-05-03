package shared.com.ortb.model

import shared.io.helpers.EmptyValidateHelper

case class EntityWithJoinedTableRowsModel[TBase, TChildRowsType](
  row : Option[TBase],
  innerRows : Option[Seq[TChildRowsType]]
) {
  lazy val hasRow : Boolean = EmptyValidateHelper.isDefined(row)
  lazy val hasInnerRows : Boolean = EmptyValidateHelper.hasAnyItem(innerRows)
}
