package services.core.traits

import services.core.traits.implementations._
import shared.com.ortb.persistent.repositories.pattern.adapters.RepositoryWrapperAdapterImplementation
import shared.com.ortb.persistent.repositories.pattern.traits.adapters.{RepositoryJsonAdapter, RepositoryWrapperAdapter}

trait BasicPersistentServiceContracts[TTable, TRow, TKey]
  extends BasicPersistentServiceOperationsImplementation[TTable, TRow, TKey]
    with MultiplePersistentServiceOperationImplementation[TTable, TRow, TKey]
    with RepositoryWrapperAdapter[TTable, TRow, TKey]
    with RepositoryJsonAdapter[TTable, TRow, TKey]
    with BasicPersistentServiceAdapterOperationsImplementation[TTable, TRow, TKey]
