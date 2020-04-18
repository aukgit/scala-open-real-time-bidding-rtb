package services.core.traits.implementations

import io.circe.{Decoder, Encoder}
import services.core.traits.BasicPersistentServiceAdapterOperations
import shared.com.ortb.model.wrappers.persistent._

trait BasicPersistentServiceAdapterOperationsImplementation[TTable, TRow, TKey]
    extends BasicPersistentServiceAdapterOperations[TTable, TRow, TKey] {
  override def toEntityWrapper(
      item: Option[TRow]): Option[EntityWrapper[TRow, TKey]] =
    repository.toEntityWrapper(item)

  override def fromEntityWrapperToEntityWrapperWithOptions(
      item: Option[EntityWrapper[TRow, TKey]])
    : Option[EntityWrapperWithOptions[TRow, TKey]] =
    repository.fromEntityWrapperToEntityWrapperWithOptions(item)

  override def fromEntityWrapperWithOptionsToEntityWrapper(
      item: Option[EntityWrapperWithOptions[TRow, TKey]])
    : Option[EntityWrapper[TRow, TKey]] =
    repository.fromEntityWrapperWithOptionsToEntityWrapper(item)

  override def toEntityWrapperWithOptions(
      item: Option[TRow]): Option[EntityWrapperWithOptions[TRow, TKey]] =
    repository.toEntityWrapperWithOptions(item)

  override def toEntitiesWrapper(items: Option[List[TRow]])
    : Option[List[EntityWrapper[TRow, TKey]]] =
    repository.toEntitiesWrapper(items)

  override def toEntitiesWrapperWithOptions(items: Option[List[TRow]])
    : Option[List[EntityWrapperWithOptions[TRow, TKey]]] =
    repository.toEntitiesWrapperWithOptions(items)

  override def fromEntityToJson(entity: Option[TRow])(
      implicit encoder: Encoder[TRow]): Option[String] =
    repository.fromEntityToJson(entity)

  override def fromEntitiesToJson(entities: Option[List[TRow]])(
      implicit encoder: Encoder[List[TRow]]): Option[String] =
    repository.fromEntitiesToJson(entities)(encoder)

  override def fromJsonToEntityWrapper(jsonContent: Option[String])(
      implicit decoder: Decoder[TRow])
    : Option[EntityWrapperWithOptions[TRow, TKey]] =
    repository.fromJsonToEntityWrapper(jsonContent)(decoder)

  override def fromJsonToEntitiesWrapper(jsonContent: Option[String])(
      implicit decoder: Decoder[List[TRow]])
    : Option[List[EntityWrapperWithOptions[TRow, TKey]]] =
    repository.fromJsonToEntitiesWrapper(jsonContent)(decoder)

  def convertItemTo[A, B](
      item: Option[A],
      adapterLogic: PartialFunction[Option[A], Option[B]]): Option[B] =
    repository.convertItemTo(item, adapterLogic)

  def convertItemsTo[A, B](
      items: Option[Iterable[A]],
      adapterLogic: PartialFunction[Option[Iterable[A]], Option[Iterable[B]]])
    : Option[Iterable[B]] =
    repository.convertItemsTo(items, adapterLogic)
}
