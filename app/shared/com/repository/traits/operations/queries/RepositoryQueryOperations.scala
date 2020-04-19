package shared.com.repository.traits.operations.queries

import shared.com.repository.RepositoryBase
import shared.com.repository.traits.implementions.operations.queries.RepositoryGetPagedQueryOperationsImplementation
import shared.com.repository.traits.{RepositoryEntityGettersSetters, SingleRepositoryBase}
import slick.jdbc.SQLiteProfile.api._
import slick.lifted.Query

import scala.concurrent.{Await, Future}

trait RepositoryQueryOperations[TTable, TRow, TKey]
  extends RepositoryGetAllQueryOperations[TTable, TRow, TKey]
    with RepositoryGetByIdQueryOperations[TTable, TRow, TKey]
    with RepositorySingleQueryOperations[TTable, TRow, TKey]
    with RepositoryGetPagedQueryOperations[TRow]

trait RepositoryQueryOperationsImplementation[TTable, TRow, TKey]
  extends RepositoryQueryOperations[TTable, TRow, TKey]
    with RepositoryGetAllQueryOperationsImplementation[TTable, TRow, TKey]
    with RepositoryGetByIdQueryOperationsImplementation[TTable, TRow, TKey]
    with RepositorySingleQueryOperationsImplementation[TTable, TRow, TKey]
    with RepositoryGetPagedQueryOperationsImplementation[TTable, TRow, TKey] {
  this : RepositoryBase[TTable, TRow, TKey] =>
}



trait RepositorySingleQueryOperationsImplementation[TTable, TRow, TKey]
  extends RepositorySingleQueryOperations[TTable, TRow, TKey] {
  this : RepositoryBase[TTable, TRow, TKey] =>
  def isEmptyGivenEntity(entityId: Option[TKey],
    entity: Option[TRow]): Boolean =
    entityId.isEmpty || entity.isEmpty

  def getFirstOrDefault(rows: Future[Seq[TRow]]): Option[TRow] = {
    this.getFirstOrDefault(Await.result(rows, defaultTimeout))
  }

  def getFirstOrDefault(rows: Seq[TRow]): Option[TRow] = {
    if (rows != null && rows.nonEmpty) {
      return Some(rows.head)
    }

    None
  }
}

trait RepositoryGetAllQueryOperationsImplementation[TTable, TRow, TKey]
  extends RepositoryGetAllQueryOperations[TTable, TRow, TKey] {
  this : RepositoryBase[TTable, TRow, TKey] =>
  def getAllAsList: List[TRow] =
    toRegular(
      this.runAsync(getAllQuery.result)
          .map(allSeqItems=> allSeqItems.toList),
      defaultTimeout)

  def getAll: Seq[TRow] = toRegular(this.runAsync(getAllQuery.result), defaultTimeout)

  def getAllAsync: Future[Seq[TRow]] = this.runAsync(getAllQuery.result)
}

trait RepositoryGetByIdQueryOperationsImplementation[TTable, TRow, TKey]
  extends RepositoryGetByIdQueryOperations[TTable, TRow, TKey] {
  this : RepositoryBase[TTable, TRow, TKey] =>
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
}