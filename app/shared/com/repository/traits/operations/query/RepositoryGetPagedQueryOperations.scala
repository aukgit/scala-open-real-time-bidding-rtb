package shared.com.repository.traits.operations.query

import shared.com.repository.traits.operations.mutations.RepositoryOperationsBase

import scala.concurrent.Future

trait RepositoryGetPagedQueryOperations[TTable, TRow, TKey]
  extends RepositoryOperationsBase[TRow] {

  def defaultPagedItems = 100

  def limit(rows : Future[Seq[TRow]], limit: Int) : Seq[TRow]

  def getPaged(
    rows : Seq[TRow],
    pageIndex: Int,
    eachPageItems: Int = defaultPagedItems) : Seq[TRow]
}
