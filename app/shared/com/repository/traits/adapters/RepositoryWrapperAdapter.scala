package shared.com.repository.traits.adapters

import shared.com.ortb.model.wrappers.persistent.{EntityWrapper, EntityWrapperWithOptions}
import shared.com.repository.traits.operations.mutations.RepositoryOperationsBase

trait RepositoryWrapperAdapter[TTable, TRow, TKey]
    extends RepositoryOperationsBase[TRow] {
  def toEntityWrapper(item: Option[TRow]): Option[EntityWrapper[TRow, TKey]]

  def fromEntityWrapperToEntityWrapperWithOptions(
      item: Option[EntityWrapper[TRow, TKey]])
    : Option[EntityWrapperWithOptions[TRow, TKey]]

  def fromEntityWrapperWithOptionsToEntityWrapper(
    item: Option[EntityWrapperWithOptions[TRow, TKey]])
  : Option[EntityWrapper[TRow, TKey]]

  def toEntityWrapperWithOptions(
      item: Option[TRow]): Option[EntityWrapperWithOptions[TRow, TKey]]

  def toEntitiesWrapper(items: Option[List[TRow]])
    : Option[List[EntityWrapper[TRow, TKey]]]

  def toEntitiesWrapperWithOptions(items: Option[List[TRow]])
    : Option[List[EntityWrapperWithOptions[TRow, TKey]]]
}
