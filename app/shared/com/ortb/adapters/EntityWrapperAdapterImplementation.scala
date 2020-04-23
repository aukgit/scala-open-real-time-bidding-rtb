package shared.com.ortb.adapters

import shared.com.ortb.model.results.{ RepositoryOperationResultModel, RepositoryOperationResultsModel }
import shared.com.ortb.model.wrappers.persistent.{ EntityWrapper, EntityWrapperWithOptions }

trait EntityWrapperAdapterImplementation {
  def fromResultsModelToEntityWrap[TRow, TKey](
    inputModel : RepositoryOperationResultsModel[TRow, TKey]) : List[EntityWrapper[TRow, TKey]] =
    inputModel.data.map(r => EntityWrapper(r.entityId, r.entity)).toList

  def fromResultModelToEntityWrap[TRow, TKey](
    inputModel : RepositoryOperationResultModel[TRow, TKey]) : EntityWrapper[TRow, TKey] =
    inputModel.data.get

  def fromEntityWrapperToEntityWrapperWithOptions[TRow, TKey](
    inputModel : EntityWrapper[TRow, TKey]) : EntityWrapperWithOptions[TRow, TKey] =
    EntityWrapperWithOptions(Some(inputModel.entityId), Some(inputModel.entity))

  def fromEntityWrapperOptionsToEntityWrapper[TRow, TKey](
    inputModel : EntityWrapperWithOptions[TRow, TKey]) : EntityWrapper[TRow, TKey] =
    EntityWrapper(inputModel.entityId.get, inputModel.entity.get)

  def fromEntityWrapperWithOptionsToEntityWrapper[TRow, TKey](
    inputModel : EntityWrapperWithOptions[TRow, TKey]) : EntityWrapper[TRow, TKey] =
    EntityWrapper(inputModel.entityId.get, inputModel.entity.get)

  def fromEntityWrappersToEntities[TRow, TKey](
    inputModel : Iterable[EntityWrapper[TRow, TKey]]) : List[TRow] =
    inputModel.map(r => r.entity).toList
}
