package shared.com.repository.traits

import shared.com.repository.traits.operations.mutations._

trait SingleRepositoryBase[TTable, TRow, TKey]
  extends RepositoryOperationsAsync[TTable, TRow, TKey]
    with RepositoryOperations[TTable, TRow, TKey]
