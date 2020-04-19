package shared.com.repository.traits.operations.query

import shared.com.repository.traits.operations.mutations.RepositoryOperationsBase
import slick.lifted.{Query, TableQuery}

import scala.concurrent.Future

trait RepositoryQueryOperations[TTable, TRow, TKey]
  extends RepositoryOperationsBase[TRow]{

  def table: TableQuery[_]

  def tableName: String

  def getAllAsList: List[TRow]

  def getAll: Seq[TRow]

  def getAllAsync: Future[Seq[TRow]]

  def isExists(entityId: TKey): Boolean

  def getById(entityId: TKey): Option[TRow]

  def getQueryByIdSingle(id: TKey): Query[TTable, TRow, Seq]

  def getQueryById(id: TKey): Query[TTable, TRow, Seq]

  def isEmptyGivenEntity(entityId: Option[TKey],
    entity: Option[TRow]): Boolean

  def getAllQuery: Query[TTable, TRow, Seq]

  def getEntityId(entity: Option[TRow]): TKey

  def setEntityId(entityId: Option[TKey], entity: Option[TRow]): Option[TRow]

  protected def getFirstOrDefault(rows: Future[Seq[TRow]]): Option[TRow]

  protected def getFirstOrDefault(rows: Seq[TRow]): Option[TRow]
}
