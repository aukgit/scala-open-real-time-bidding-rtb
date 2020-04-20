package shared.com.repository.traits.implementions.operations.queries

import shared.com.repository.RepositoryBase
import shared.com.repository.traits.operations.queries._

trait RepositoryQueryOperationsImplementation[TTable, TRow, TKey]
  extends RepositoryQueryOperations[TTable, TRow, TKey]
    with RepositoryGetAllQueryOperationsImplementation[TTable, TRow, TKey]
    with RepositoryGetByIdQueryOperationsImplementation[TTable, TRow, TKey]
    with RepositorySingleQueryOperationsImplementation[TTable, TRow, TKey]
    with RepositoryGetPagedQueryOperationsImplementation[TTable, TRow, TKey] {
  this : RepositoryBase[TTable, TRow, TKey] =>
}
