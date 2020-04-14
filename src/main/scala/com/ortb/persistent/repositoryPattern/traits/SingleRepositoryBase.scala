package com.ortb.persistent.repositoryPattern.traits

import com.ortb.persistent.repositoryPattern.Repository
import slick.lifted.{TableQuery, Query}

import scala.concurrent.{Future, Await}

trait SingleRepositoryBase[TTable, TRow, TKey]
  extends
    RepositoryOperationsAsync[TTable, TRow, TKey]
    with
    RepositoryOperations[TTable, TRow, TKey] {
  this : Repository[TTable, TRow, TKey] =>

  def table : TableQuery[_]

  def tableName : String

  def getAll : List[TRow] = toRegular(getAllAsync, defaultTimeout).toList

  def isExists(entityId : TKey) : Boolean = getById(entityId) != null

  def getQueryByIdSingle(id : TKey) : Query[TTable, TRow, Seq] = getQueryById(id).take(1)

  def getQueryById(id : TKey) : Query[TTable, TRow, Seq]

  def getById(entityId : TKey) : TRow

  def getAllAsync : Future[Seq[TRow]]

  def getIdOf(entity : TRow) : TKey

  protected def getFirstOrDefault(rows : Future[Seq[TRow]]) : Option[TRow] = {
    this.getFirstOrDefault(Await.result(rows, defaultTimeout))
  }

  protected def getFirstOrDefault(rows : Seq[TRow]) : Option[TRow] = {
    if (rows != null && rows.nonEmpty) {
      Some(rows.head)
    }
    else {
      None
    }
  }
}
