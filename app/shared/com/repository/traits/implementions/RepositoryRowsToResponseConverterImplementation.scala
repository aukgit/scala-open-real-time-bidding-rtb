package shared.com.repository.traits.implementions

import shared.com.ortb.enumeration.DatabaseActionType.DatabaseActionType
import shared.com.ortb.model.attributes.GenericResponseAttributesModel
import shared.com.ortb.model.results.{ RepositoryOperationResultModel, RepositoryOperationResultsModel }
import shared.com.ortb.model.wrappers.persistent.EntityWrapperModel
import shared.com.repository.RepositoryBase
import shared.com.repository.traits.RepositoryRowsToResponseConverter
import shared.io.helpers.EmptyValidateHelper

trait RepositoryRowsToResponseConverterImplementation[TTable, TRow, TKey] extends RepositoryRowsToResponseConverter[TTable, TRow, TKey] {
  this : RepositoryBase[TTable, TRow, TKey] =>
  def getRowsToResponse(
    rows : Option[Iterable[TRow]],
    dbAction : Option[DatabaseActionType] = None,
    message : String = "") : RepositoryOperationResultsModel[TRow, TKey] = {
    if (EmptyValidateHelper.isItemsEmpty(rows)) {
      return null
    }

    val rowsToEntityRows = rows.get.map(row => EntityWrapperModel(getEntityId(row), row))
    val idsList = Some(rowsToEntityRows.map(w => w.entityId.toString).toList)

    val attributes = GenericResponseAttributesModel(
      isSuccess = true,
      id = Some(idsList.get.head.toString),
      ids = idsList,
      dbAction,
      message)


    val repositoryResponse = RepositoryOperationResultsModel(
      Some(attributes),
      rowsToEntityRows
    )

    repositoryResponse
  }

  def getRowToResponse(
    rowOption : Option[TRow],
    dbAction : Option[DatabaseActionType],
    message : String = "") : RepositoryOperationResultModel[TRow, TKey] = {
    if (EmptyValidateHelper.isEmpty(rowOption)) {
      return null
    }

    val row = rowOption.get
    val entity = Some(EntityWrapperModel(getEntityId(row), row))

    val attributes = GenericResponseAttributesModel(
      isSuccess = true,
      id = Some(entity.get.entityId.toString),
      ids = None,
      dbAction,
      message)

    val repositoryResponse = RepositoryOperationResultModel(
      Some(attributes),
      entity
    )

    repositoryResponse
  }
}
