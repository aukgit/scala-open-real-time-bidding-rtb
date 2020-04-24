package shared.com.repository.traits.operations.queries

trait RepositoryQueryOperations[TTable, TRow, TKey]
  extends RepositoryGetAllQueryOperations[TTable, TRow, TKey]
    with RepositoryGetByIdQueryOperations[TTable, TRow, TKey]
    with RepositorySingleQueryOperations[TTable, TRow, TKey]
    with RepositoryGetPagedQueryOperations[TRow]