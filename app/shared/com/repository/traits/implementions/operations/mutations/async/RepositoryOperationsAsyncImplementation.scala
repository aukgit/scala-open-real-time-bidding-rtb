package shared.com.repository.traits.implementions.operations.mutations.async

import shared.com.repository.RepositoryBase
import shared.com.repository.traits.operations.mutations.async._

trait RepositoryOperationsAsyncImplementation[TTable, TRow, TKey]
  extends RepositoryOperationsAsync[TTable, TRow, TKey]
    with RepositoryAddOperationsAsyncImplementation[TTable, TRow, TKey]
    with RepositoryUpdateOperationsAsyncImplementation[TTable, TRow, TKey]
    with RepositoryDeleteOperationsAsyncImplementation[TTable, TRow, TKey]
    with RepositoryAddOrUpdateOperationsAsyncImplementation[TTable, TRow, TKey] {
  this : RepositoryBase[TTable, TRow, TKey] =>
}
