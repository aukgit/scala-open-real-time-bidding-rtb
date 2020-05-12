package shared.com.repository.traits.operations.mutations.async

trait RepositoryOperationsAsync[TTable, TRow, TKey]
  extends RepositoryAddOperationsAsync[TTable, TRow, TKey]
    with RepositoryUpdateOperationsAsync[TTable, TRow, TKey]
    with RepositoryDeleteOperationsAsync[TTable, TRow, TKey]
    with RepositoryAddOrUpdateOperationsAsync[TTable, TRow, TKey]
