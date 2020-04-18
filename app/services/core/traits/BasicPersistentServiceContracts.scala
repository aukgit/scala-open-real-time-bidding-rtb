package services.core.traits

import shared.com.ortb.persistent.repositories.pattern.traits.adapters.RepositoryWrapperAdapter

trait BasicPersistentServiceContracts[TTable, TRow, TKey]
  extends BasicPersistentServiceOperations[TTable, TRow, TKey]
    with MultiplePersistentServiceOperation[TTable, TRow, TKey]
    with RepositoryWrapperAdapter[TTable, TRow, TKey]
