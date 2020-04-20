package services.core.traits

import shared.com.repository.RepositoryBase
import shared.com.repository.traits.operations.mutations.RepositoryOperations

trait BasicPersistentServiceCore[TTable, TRow, TKey]{
  val repository : RepositoryBase[TTable, TRow, TKey]
}
