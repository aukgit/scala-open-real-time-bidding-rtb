package services.core.traits

import services.core.traits.implementations._
import shared.com.repository.traits.adapters.{RepositoryJsonAdapter, RepositoryWrapperAdapter}
import shared.com.repository.traits.implementions.adapters.RepositoryWrapperAdapterImplementation

trait BasicPersistentServiceContracts[TTable, TRow, TKey]
  extends BasicPersistentServiceOperationsImplementation[TTable, TRow, TKey]
    with MultiplePersistentServiceOperationImplementation[TTable, TRow, TKey]
    with RepositoryWrapperAdapter[TTable, TRow, TKey]
    with RepositoryJsonAdapter[TTable, TRow, TKey]
    with BasicPersistentServiceAdapterOperationsImplementation[TTable, TRow, TKey]
