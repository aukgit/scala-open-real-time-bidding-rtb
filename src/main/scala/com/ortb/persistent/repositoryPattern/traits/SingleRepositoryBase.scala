package com.ortb.persistent.repositoryPattern.traits

import com.ortb.persistent.repositoryPattern.Repository
import slick.lifted.{TableQuery, AbstractTable, Query}

import scala.concurrent.{Future, Await}

trait SingleRepositoryBase[TTable <: AbstractTable[_], TRow <: Null, TKey]
  extends
    RepositoryOperationsAsync[TTable, TRow, TKey] with
    SingleRepositoryBase[TTable, TRow, TKey] with
    EntityResponseCreator[TTable, TRow, TKey] with
    DatabaseActionExecutor[TTable, TRow, TKey] {
  this: Repository[TTable, TRow, TKey] =>

  def table: TableQuery[TTable]

  def getAll: List[TRow]

  def getById(entityId: TKey): TRow

  def getAllAsync: Future[Seq[TRow]]

  def isExists(entityId: TKey): Boolean

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
}
