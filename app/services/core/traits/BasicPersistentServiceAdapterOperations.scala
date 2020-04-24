package services.core.traits

import shared.com.repository.traits.adapters.{RepositoryJsonAdapter, RepositoryWrapperAdapter}

trait BasicPersistentServiceAdapterOperations[TTable, TRow, TKey]
  extends RepositoryWrapperAdapter[TTable, TRow, TKey]
    with RepositoryJsonAdapter[TTable, TRow, TKey]
    with BasicPersistentServiceCore[TTable, TRow, TKey]


