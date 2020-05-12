package shared.com.repository.traits

import shared.com.repository.traits.operations.mutations.RepositoryOperationsBase

trait RepositoryIdsGetter[TTable, TRow, TKey] extends RepositoryOperationsBase[TRow] {
  def getIds(rows: Option[Iterable[TRow]]): Iterable[TKey]

  def getIdsAsString(rows: Option[Iterable[TRow]]): Iterable[String]

  def getRowToIdsAsString(row: Option[TRow]): Iterable[String]
}
