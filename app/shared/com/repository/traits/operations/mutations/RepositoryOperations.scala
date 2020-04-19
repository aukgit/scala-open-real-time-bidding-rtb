package shared.com.repository.traits.operations.mutations

trait RepositoryOperations[TTable, TRow, TKey]
  extends RepositoryAddOperations[TTable, TRow, TKey]
    with RepositoryUpdateOperations[TTable, TRow, TKey]
    with RepositoryAddOrUpdateOperations[TTable, TRow, TKey]
    with RepositoryDeleteOperations[TTable, TRow, TKey]
