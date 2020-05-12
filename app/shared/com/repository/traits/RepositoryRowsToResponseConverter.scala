package shared.com.repository.traits

import shared.com.ortb.enumeration.DatabaseActionType
import shared.com.ortb.model.results.{ RepositoryOperationResultModel, RepositoryOperationResultsModel }

trait RepositoryRowsToResponseConverter[TTable, TRow, TKey] {
  def getRowsToResponse(
    rows: Option[Iterable[TRow]] ,
    dbAction : Option[DatabaseActionType] = None,
    message : String = ""): RepositoryOperationResultsModel[TRow, TKey]

  def getRowToResponse(
    rowOption: Option[TRow] ,
    dbAction : Option[DatabaseActionType],
    message : String = ""): RepositoryOperationResultModel[TRow, TKey]
}
