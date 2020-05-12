package shared.com.repository.traits.implementions.operations.queries

import shared.com.repository.RepositoryBase
import shared.com.repository.traits.operations.queries.RepositoryGetByIdQueryOperations
import slick.lifted.Query
import slick.jdbc.SQLiteProfile.api._

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
