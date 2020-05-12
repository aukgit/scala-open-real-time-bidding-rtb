package shared.com.repository.traits.implementions.adapters

import shared.com.ortb.constants.AppConstants
import shared.com.ortb.model.wrappers.persistent.{ EntityWrapperModel, EntityWrapperWithOptions }
import shared.com.repository.RepositoryBase
import shared.com.repository.traits.adapters.RepositoryWrapperAdapter
import shared.io.helpers.EmptyValidateHelper
import shared.io.loggers.AppLogger

import scala.collection.mutable.ArrayBuffer

trait RepositoryWrapperAdapterImplementation[TTable, TRow, TKey]
    extends RepositoryWrapperAdapter[TTable, TRow, TKey] {
  this: RepositoryBase[TTable, TRow, TKey] =>

  //noinspection DuplicatedCode
  override def toEntityWrapper(
      item: Option[TRow]): Option[EntityWrapperModel[TRow, TKey]] = {
    val isEmpty =
      EmptyValidateHelper.isEmpty(item, Some(AppConstants.NoContent))

    if (isEmpty) {
      return None
    }

    val id = getEntityIdFromOptionRow(item)

    Some(EntityWrapperModel(id, entity = item.get))
  }

  //noinspection DuplicatedCode
  override def toEntityWrapperWithOptions(
      item: Option[TRow]): Option[EntityWrapperWithOptions[TRow, TKey]] = {
    val isEmpty =
      EmptyValidateHelper.isEmpty(item, Some(AppConstants.NoContent))

    if (isEmpty) {
      return None
    }

    val id = getEntityIdFromOptionRow(item)

    Some(EntityWrapperWithOptions(Some(id), entity = item))
  }

  override def fromEntityWrapperToEntityWrapperWithOptions(
      item: Option[EntityWrapperModel[TRow, TKey]])
    : Option[EntityWrapperWithOptions[TRow, TKey]] = {
    val isEmpty = EmptyValidateHelper.isEmpty(item)

    if (isEmpty) {
      return None
    }

    try {
      val entityWrapper = item.get
      val id = entityWrapper.entityId
      val eValue = entityWrapper.entity
      val entityWrapperWithOptions = EntityWrapperWithOptions[TRow, TKey](
        Some(id),
        Some(eValue)
      )

      return Some(entityWrapperWithOptions)
    } catch {
      case e: Exception => AppLogger.error(e)
    }

    None
  }

  override def fromEntityWrapperWithOptionsToEntityWrapper(
      item: Option[EntityWrapperWithOptions[TRow, TKey]])
    : Option[EntityWrapperModel[TRow, TKey]] = {
    val isEmpty = EmptyValidateHelper.isEmpty(item)

    if (isEmpty) {
      return None
    }

    try {
      val entityWrapperWithOptions = item.get
      val id: TKey = entityWrapperWithOptions.entityId.get
      val entity: TRow = entityWrapperWithOptions.entity.get
      val entityWrapper = EntityWrapperModel[TRow, TKey](
        id,
        entity
      )

      return Some(entityWrapper)
    } catch {
      case e: Exception => AppLogger.error(e)
    }

    None
  }

  override def toEntitiesWrapperWithOptions(items: Option[Iterable[TRow]])
    : Option[Iterable[EntityWrapperWithOptions[TRow, TKey]]] = {
    val isEmpty =
      EmptyValidateHelper.isItemsEmpty(items, Some(AppConstants.NoContent))

    if (isEmpty) {
      return None
    }

    val results = items.get
      .map(entity => toEntityWrapperWithOptions(Some(entity)).get)
    Some(results)
  }

  override def toEntitiesWrapper(items: Option[Iterable[TRow]])
    : Option[Iterable[EntityWrapperModel[TRow, TKey]]] = {
    val isEmpty =
      EmptyValidateHelper.isItemsEmpty(items, Some(AppConstants.NoContent))

    if (isEmpty) {
      return None
    }

    val results = items.get
      .map(entity => toEntityWrapper(Some(entity)).get)
    Some(results)
  }
}
