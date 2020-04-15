package com.ortb.persistent.repositoryPattern.traits

import com.ortb.persistent.repositoryPattern.Repository
import slick.lifted.{TableQuery, Query}
import slick.jdbc.SQLiteProfile.api._

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

  def getAllAsync : Future[Seq[TRow]] = this.runAsync(getAllQuery.result)

  def isExists(entityId : TKey) : Boolean = getById(entityId).isDefined

  def getById(entityId : TKey) : Option[TRow] = {
    val results = this.run(getQueryByIdSingle(entityId).result)

    if (results.isEmpty) {
      return None
    }

    Some(results.head)
  }

  def getQueryByIdSingle(id : TKey) : Query[TTable, TRow, Seq] = getQueryById(id).take(1)

  def getQueryById(id : TKey) : Query[TTable, TRow, Seq]

  def isEmptyGivenEntity(
    entityId : Option[TKey],
    entity                        : Option[TRow]) : Boolean = entityId.isEmpty || entity.isEmpty

  def getAllQuery : Query[TTable, TRow, Seq]

  def getEntityId(entity : Option[TRow]) : TKey

  def setEntityId(entityId : Option[TKey], entity : Option[TRow]) : Option[TRow]

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
