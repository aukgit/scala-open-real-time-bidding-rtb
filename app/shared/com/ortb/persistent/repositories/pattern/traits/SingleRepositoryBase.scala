package shared.com.ortb.persistent.repositories.pattern.traits

import shared.com.ortb.persistent.repositories.pattern.RepositoryBase
import slick.lifted.{Query, TableQuery}
import slick.jdbc.SQLiteProfile.api._

import scala.concurrent.{Await, Future}

trait SingleRepositoryBase[TTable, TRow, TKey]
    extends RepositoryOperationsAsync[TTable, TRow, TKey]
    with RepositoryOperations[TTable, TRow, TKey] {
  this: RepositoryBase[TTable, TRow, TKey] =>

  def table: TableQuery[_]

  def tableName: String

  def getAllAsList: List[TRow] =
    toRegular(
      this.runAsync(
        getAllQuery.result)
          .map(allSeqItems=> allSeqItems.toList),
      defaultTimeout)

  def getAll: Seq[TRow] = toRegular(this.runAsync(getAllQuery.result), defaultTimeout)

  def getAllAsArray: Array[TRow] =
    toRegular(
      this.runAsync(
        getAllQuery.result)
          .map(allSeqItems=> allSeqItems.toArray),
      defaultTimeout)

  def getAllAsync: Future[Seq[TRow]] = this.runAsync(getAllQuery.result)

  def isExists(entityId: TKey): Boolean = getById(entityId).isDefined

  def getById(entityId: TKey): Option[TRow] = {
    val results = this.run(getQueryByIdSingle(entityId).result)

    if (results == null || results.isEmpty) {
      return None
    }

    Some(results.head)
  }

  def getQueryByIdSingle(id: TKey): Query[TTable, TRow, Seq] =
    getQueryById(id).take(1)

  def getQueryById(id: TKey): Query[TTable, TRow, Seq]

  def isEmptyGivenEntity(entityId: Option[TKey],
                         entity: Option[TRow]): Boolean =
    entityId.isEmpty || entity.isEmpty

  def getAllQuery: Query[TTable, TRow, Seq]

  def getEntityId(entity: Option[TRow]): TKey

  def setEntityId(entityId: Option[TKey], entity: Option[TRow]): Option[TRow]

  protected def getFirstOrDefault(rows: Future[Seq[TRow]]): Option[TRow] = {
    this.getFirstOrDefault(Await.result(rows, defaultTimeout))
  }

  protected def getFirstOrDefault(rows: Seq[TRow]): Option[TRow] = {
    if (rows != null && rows.nonEmpty) {
      return Some(rows.head)
    }

    None
  }
}
