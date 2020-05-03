package shared.com.ortb.adapters.traits.implementations

import shared.com.ortb.adapters.traits.EntityWrapperAdapter
import shared.com.ortb.model.results.{ RepositoryOperationResultModel, RepositoryOperationResultsModel }
import shared.com.ortb.model.wrappers.persistent.{ EntityWrapperModel, EntityWrapperWithOptions }

trait EntityWrapperAdapterImplementation extends EntityWrapperAdapter{
  def fromResultsModelToEntityWrap[TRow, TKey](
    inputModel : RepositoryOperationResultsModel[TRow, TKey]) : List[EntityWrapperModel[TRow, TKey]] =
    inputModel.data.map(r => EntityWrapperModel(r.entityId, r.entity)).toList

  def fromResultModelToEntityWrap[TRow, TKey](
    inputModel : RepositoryOperationResultModel[TRow, TKey]) : EntityWrapperModel[TRow, TKey] =
    inputModel.data.get

  def fromEntityWrapperToEntityWrapperWithOptions[TRow, TKey](
    inputModel : EntityWrapperModel[TRow, TKey]) : EntityWrapperWithOptions[TRow, TKey] =
    EntityWrapperWithOptions(Some(inputModel.entityId), Some(inputModel.entity))

  def fromEntityWrapperOptionsToEntityWrapper[TRow, TKey](
    inputModel : EntityWrapperWithOptions[TRow, TKey]) : EntityWrapperModel[TRow, TKey] =
    EntityWrapperModel(inputModel.entityId.get, inputModel.entity.get)

  def fromEntityWrapperWithOptionsToEntityWrapper[TRow, TKey](
    inputModel : EntityWrapperWithOptions[TRow, TKey]) : EntityWrapperModel[TRow, TKey] =
    EntityWrapperModel(inputModel.entityId.get, inputModel.entity.get)

  def fromEntityWrappersToEntities[TRow, TKey](
    inputModel : Iterable[EntityWrapperModel[TRow, TKey]]) : List[TRow] =
    inputModel.map(r => r.entity).toList
}
