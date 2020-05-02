package shared.com.ortb.model

import shared.com.ortb.model.wrappers.persistent.EntityWrapper
import shared.io.helpers.EmptyValidateHelper

case class EntityWrapperWithJoinedTableRows[TBase, TBaseKey, TChildRowsType, TChildKey](
  row : Option[EntityWrapper[TBase, TBaseKey]],
  innerRows : Option[Seq[EntityWrapper[TChildRowsType, TChildKey]]]
) {
  lazy val hasRow : Boolean = EmptyValidateHelper.isDefined(row) && row.get.entity != null
  lazy val hasInnerRows : Boolean = EmptyValidateHelper.hasAnyItem(innerRows)
}
