package shared.com.repository.traits

import shared.com.ortb.enumeration.DatabaseActionType.DatabaseActionType
import shared.com.ortb.model.results.RepositoryOperationResultsModel

trait RepositoryRowsToResponseConverter[TTable, TRow, TKey] {
  def getRowsToResponse(
    rows: Option[Iterable[TRow]] ,
    dbAction : Option[DatabaseActionType] = None,
    message : String = ""): RepositoryOperationResultsModel[TRow, TKey]
}
