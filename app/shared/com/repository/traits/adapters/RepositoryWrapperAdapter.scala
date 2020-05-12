package shared.com.repository.traits.adapters

import shared.com.ortb.model.wrappers.persistent.{ EntityWrapperModel, EntityWrapperWithOptions }
import shared.com.repository.traits.operations.mutations.RepositoryOperationsBase

import scala.collection.mutable.ArrayBuffer

trait RepositoryWrapperAdapter[TTable, TRow, TKey]
    extends RepositoryOperationsBase[TRow] {
  def toEntityWrapper(item: Option[TRow]): Option[EntityWrapperModel[TRow, TKey]]

  def fromEntityWrapperToEntityWrapperWithOptions(
      item: Option[EntityWrapperModel[TRow, TKey]])
    : Option[EntityWrapperWithOptions[TRow, TKey]]

  def fromEntityWrapperWithOptionsToEntityWrapper(
    item: Option[EntityWrapperWithOptions[TRow, TKey]])
  : Option[EntityWrapperModel[TRow, TKey]]

  def toEntityWrapperWithOptions(
      item: Option[TRow]): Option[EntityWrapperWithOptions[TRow, TKey]]

  def toEntitiesWrapper(items: Option[Iterable[TRow]])
    : Option[Iterable[EntityWrapperModel[TRow, TKey]]]

  def toEntitiesWrapperWithOptions(items: Option[Iterable[TRow]])
    : Option[Iterable[EntityWrapperWithOptions[TRow, TKey]]]
}
