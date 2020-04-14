package com.ortb.persistent.repositoryPattern.traits

import com.ortb.persistent.repositoryPattern.Repository
import slick.lifted.{TableQuery, AbstractTable, Query}
import slick.jdbc.SQLiteProfile.api._
import scala.concurrent.{Future, Await}

trait SingleRepositoryBase[TTable, TRow, TKey]
  extends
    RepositoryOperationsAsync[TTable, TRow, TKey] {
  this: Repository[TTable, TRow, TKey] =>

  def table: TableQuery[_]

  def getAll: List[TRow]

  def getById(entityId: TKey): TRow

  def getAllAsync: Future[Seq[TRow]]

  def isExists(entityId: TKey): Boolean = getById(entityId) != null

  protected def getFirstOrDefault(rows: Future[Seq[TRow]]): Option[TRow] = {
    this.getFirstOrDefault(Await.result(rows, defaultTimeout))
  }

  protected def getFirstOrDefault(rows: Seq[TRow]): Option[TRow] = {
    if (rows != null && rows.nonEmpty) {
      Some(rows.head)
    } else {
      None
    }
  }

  def getQueryById(id: TKey): Query[TTable, TRow, Seq]

  def getQueryByIdSingle(id: TKey): Query[TTable, TRow, Seq] = getQueryById(id).take(1)
}
