package shared.com.ortb.adapters.traits

import shared.com.ortb.model.results.{ RepositoryOperationResultModel, RepositoryOperationResultsModel }
import shared.com.ortb.model.wrappers.persistent.{ EntityWrapper, EntityWrapperWithOptions }

trait EntityWrapperAdapter {
  def fromResultsModelToEntityWrap[TRow, TKey](
    inputModel : RepositoryOperationResultsModel[TRow, TKey]) : List[EntityWrapper[TRow, TKey]]

  def fromResultModelToEntityWrap[TRow, TKey](
    inputModel : RepositoryOperationResultModel[TRow, TKey]) : EntityWrapper[TRow, TKey]

  def fromEntityWrapperToEntityWrapperWithOptions[TRow, TKey](
    inputModel : EntityWrapper[TRow, TKey]) : EntityWrapperWithOptions[TRow, TKey]

  def fromEntityWrapperOptionsToEntityWrapper[TRow, TKey](
    inputModel : EntityWrapperWithOptions[TRow, TKey]) : EntityWrapper[TRow, TKey]

  def fromEntityWrapperWithOptionsToEntityWrapper[TRow, TKey](
    inputModel : EntityWrapperWithOptions[TRow, TKey]) : EntityWrapper[TRow, TKey]

  def fromEntityWrappersToEntities[TRow, TKey](
    inputModel : Iterable[EntityWrapper[TRow, TKey]]) : List[TRow]
}




