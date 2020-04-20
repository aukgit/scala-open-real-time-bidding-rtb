package shared.com.repository.traits

import shared.com.repository.traits.operations.mutations._
import shared.com.repository.traits.operations.mutations.async.RepositoryOperationsAsync
import shared.com.repository.traits.operations.queries.RepositoryQueryOperations

trait SingleRepositoryBase[TTable, TRow, TKey]
  extends RepositoryOperationsAsync[TTable, TRow, TKey]
    with RepositoryQueryOperations[TTable, TRow, TKey]
    with RepositoryOperations[TTable, TRow, TKey]
    with RepositoryTableInfo[TTable, TRow, TKey]
    with RepositoryEntityGettersSetters[TTable, TRow, TKey]
