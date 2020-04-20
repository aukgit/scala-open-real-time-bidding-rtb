package shared.com.repository.traits.implementions.operations.queries

import shared.com.repository.RepositoryBase
import shared.com.repository.traits.{RepositoryEntityGettersSetters, RepositoryTableInfo}

trait SingleRepositoryBaseImplementation[TTable, TRow, TKey]
  extends RepositoryQueryOperationsImplementation[TTable, TRow, TKey]
    with RepositoryTableInfo[TTable, TRow, TKey]
    with RepositoryEntityGettersSetters[TTable, TRow, TKey] {
  this : RepositoryBase[TTable, TRow, TKey] =>
}
