package shared.com.repository.traits.operations.query

trait RepositoryQueryOperations[TTable, TRow, TKey]
  extends RepositoryGetAllQueryOperations[TTable, TRow, TKey]
    with RepositoryGetByIdQueryOperations[TTable, TRow, TKey]
    with RepositoryEntityGettersSettersOperations[TTable, TRow, TKey]
    with RepositorySingleQueryOperations[TTable, TRow, TKey]
    with RepositoryGetPagedQueryOperations[TTable, TRow, TKey]
