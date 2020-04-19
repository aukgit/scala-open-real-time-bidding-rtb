package shared.com.ortb.persistent.repositories.pattern.traits

import shared.com.ortb.persistent.repositories.pattern.traits.operations.mutations._

trait SingleRepositoryBase[TTable, TRow, TKey]
  extends RepositoryOperationsAsync[TTable, TRow, TKey]
    with RepositoryOperations[TTable, TRow, TKey]
