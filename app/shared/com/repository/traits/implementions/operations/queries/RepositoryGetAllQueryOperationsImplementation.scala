package shared.com.repository.traits.implementions.operations.queries

import shared.com.ortb.enumeration.DatabaseActionType
import shared.com.ortb.model.results.RepositoryOperationResultsModel
import shared.com.repository.RepositoryBase
import shared.com.repository.traits.operations.queries.RepositoryGetAllQueryOperations
import slick.jdbc.SQLiteProfile.api._

import scala.concurrent.Future

trait RepositoryGetAllQueryOperationsImplementation[TTable, TRow, TKey]
  extends RepositoryGetAllQueryOperations[TTable, TRow, TKey] {
  this : RepositoryBase[TTable, TRow, TKey] =>
  def getAllAsList : List[TRow] =
    toRegular(
      this.runAsync(getAllQuery.result)
          .map(allSeqItems => allSeqItems.toList),
      defaultTimeout)

  def getAll : Seq[TRow] = {
    val eventualRequestResults = this.runAsync(getAllQuery.result)
    toRegular(eventualRequestResults, defaultTimeout)
  }

  def getAllAsResponse : RepositoryOperationResultsModel[TRow, TKey] = {
    val allRows = getAll

    getRowsToResponse(Some(allRows), Some(DatabaseActionType.Read))
  }

  def getAllAsync : Future[Seq[TRow]] = this.runAsync(getAllQuery.result)
}
