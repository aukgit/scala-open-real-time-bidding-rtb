package shared.com.repository.traits.operations.query

import shared.com.repository.traits.operations.mutations.RepositoryOperationsBase
import slick.lifted.{Query, TableQuery}

import scala.concurrent.Future

trait RepositoryQueryOperations[TTable, TRow, TKey]
  extends RepositoryGetAllQueryOperations[TTable, TRow, TKey]
    with RepositoryGetByIdQueryOperations[TTable, TRow, TKey]
    with RepositoryEntityGettersSettersOperations[TTable, TRow, TKey]
    with RepositorySingleQueryOperations[TTable, TRow, TKey]
    with RepositoryGetPagedQueryOperations[TTable, TRow, TKey]{


}
