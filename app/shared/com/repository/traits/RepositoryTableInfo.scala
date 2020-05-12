package shared.com.repository.traits

import shared.com.repository.traits.operations.mutations.RepositoryOperationsBase
import shared.io.helpers.EmptyValidateHelper
import slick.lifted.TableQuery

trait RepositoryTableInfo[TTable, TRow, TKey]
  extends RepositoryOperationsBase[TRow] {
  def table : TableQuery[_]

  def tableName : String
}
