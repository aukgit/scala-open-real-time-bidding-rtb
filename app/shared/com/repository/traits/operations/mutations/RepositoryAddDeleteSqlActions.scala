package shared.com.repository.traits.operations.mutations

import slick.dbio.{Effect, NoStream}
import slick.sql.FixedSqlAction

trait RepositoryAddDeleteSqlActions[TTable, TRow, TKey]
  extends RepositoryOperationsBase[TRow] {
  def getAddAction(entity : TRow) : FixedSqlAction[TRow, NoStream, Effect.Write]

  def getDeleteAction(
    entityId : TKey
  ) : FixedSqlAction[Int, NoStream, Effect.Write]
}
