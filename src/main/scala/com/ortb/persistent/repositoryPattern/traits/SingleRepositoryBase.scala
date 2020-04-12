package com.ortb.persistent.repositoryPattern.traits

import com.ortb.persistent.schema.DatabaseSchema
import slick.lifted.{TableQuery, Query, Rep}

import scala.concurrent.Future

trait SingleRepositoryBase[TTable, TRow >: null, TKey]
  extends
    DatabaseSchema with
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

  def getById(id: Int): Query[TTable, TRow, Seq]
}
