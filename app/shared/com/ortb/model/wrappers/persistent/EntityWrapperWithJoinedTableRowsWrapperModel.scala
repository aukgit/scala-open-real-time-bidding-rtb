package shared.com.ortb.model.wrappers.persistent

import shared.io.helpers.EmptyValidateHelper

case class EntityWrapperWithJoinedTableRowsWrapperModel[TBase, TBaseKey, TChildRowsType, TChildKey](
  row : Option[EntityWrapperModel[TBase, TBaseKey]],
  innerRows : Option[Seq[EntityWrapperModel[TChildRowsType, TChildKey]]]
) {
  lazy val hasRow : Boolean = EmptyValidateHelper.isDefined(row) && row.get.entity != null
  lazy val hasInnerRows : Boolean = EmptyValidateHelper.hasAnyItem(innerRows)
}
