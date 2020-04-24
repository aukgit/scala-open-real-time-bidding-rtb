package shared.com.repository.traits.implementions

import shared.com.ortb.enumeration.DatabaseActionType.DatabaseActionType
import shared.com.ortb.model.attributes.GenericResponseAttributesModel
import shared.com.ortb.model.results.{ RepositoryOperationResultModel, RepositoryOperationResultsModel }
import shared.com.ortb.model.wrappers.persistent.EntityWrapper
import shared.com.repository.RepositoryBase
import shared.com.repository.traits.RepositoryRowsToResponseConverter
import shared.io.helpers.EmptyValidateHelper

trait RepositoryRowsToResponseConverterImplementation[TTable, TRow, TKey] extends RepositoryRowsToResponseConverter[TTable, TRow, TKey] {
  this : RepositoryBase[TTable, TRow, TKey] =>
  def getRowsToResponse(
    rows: Option[Iterable[TRow]] ,
    dbAction : Option[DatabaseActionType] = None,
    message : String = ""): RepositoryOperationResultsModel[TRow, TKey] = {
    if(EmptyValidateHelper.isItemsEmpty(rows)){
      return null
    }

    val attributes = GenericResponseAttributesModel(
      isSuccess = true,
      dbAction,
      message)

    val rowsToEntityRows = rows.get.map(row => EntityWrapper(getEntityId(row), row))

    val repositoryResponse = RepositoryOperationResultsModel(
      Some(attributes),
      rowsToEntityRows
    )

    repositoryResponse
  }

  def getRowToResponse(
    rowOption: Option[TRow] ,
    dbAction : Option[DatabaseActionType],
    message : String = ""): RepositoryOperationResultModel[TRow, TKey] = {
    if(EmptyValidateHelper.isEmpty(rowOption)){
      return null
    }

    val attributes = GenericResponseAttributesModel(
      isSuccess = true,
      dbAction,
      message)

    val row = rowOption.get

    val repositoryResponse = RepositoryOperationResultModel(
      Some(attributes),
      Some(EntityWrapper(getEntityId(row), row))
    )

    repositoryResponse
  }
}
