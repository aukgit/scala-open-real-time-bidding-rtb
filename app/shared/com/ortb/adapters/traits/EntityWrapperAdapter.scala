package shared.com.ortb.adapters.traits

import shared.com.ortb.model.results.{ RepositoryOperationResultModel, RepositoryOperationResultsModel }
import shared.com.ortb.model.wrappers.persistent.{ EntityWrapperModel, EntityWrapperWithOptions }

trait EntityWrapperAdapter {
  def fromResultsModelToEntityWrap[TRow, TKey](
    inputModel : RepositoryOperationResultsModel[TRow, TKey]) : List[EntityWrapperModel[TRow, TKey]]

  def fromResultModelToEntityWrap[TRow, TKey](
    inputModel : RepositoryOperationResultModel[TRow, TKey]) : EntityWrapperModel[TRow, TKey]

  def fromEntityWrapperToEntityWrapperWithOptions[TRow, TKey](
    inputModel : EntityWrapperModel[TRow, TKey]) : EntityWrapperWithOptions[TRow, TKey]

  def fromEntityWrapperOptionsToEntityWrapper[TRow, TKey](
    inputModel : EntityWrapperWithOptions[TRow, TKey]) : EntityWrapperModel[TRow, TKey]

  def fromEntityWrapperWithOptionsToEntityWrapper[TRow, TKey](
    inputModel : EntityWrapperWithOptions[TRow, TKey]) : EntityWrapperModel[TRow, TKey]

  def fromEntityWrappersToEntities[TRow, TKey](
    inputModel : Iterable[EntityWrapperModel[TRow, TKey]]) : List[TRow]
}




