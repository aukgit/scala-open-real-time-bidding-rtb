package com.ortb.persistent.repositoryPattern.traits

import slick.lifted.{TableQuery, Query, AbstractTable}

import scala.concurrent.Future

trait SingleRepositoryBase[TTable<: AbstractTable[_], TRow >: Null, TKey]
  extends
    RepositoryOperationsAsync[TRow, TKey] {

  def table: TableQuery[TTable]

  def getAll: List[TRow]

  def getById(entityId: TKey): TRow

  def getAllAsync: Future[Seq[TRow]]

  def isExists(entityId: TKey): Boolean

  protected def getFirstOrDefault(rows: Seq[TRow]): TRow = {
    if (rows != null && rows.nonEmpty) {
      rows.head
    } else {
      null
    }
  }

  def getQueryById(id: Int): Query[TTable, TRow, Seq]
}
