package services.core.traits

import services.core.traits.implementations._
import shared.com.ortb.manager.AppManager
import shared.com.repository.traits.adapters.{ RepositoryJsonAdapter, RepositoryWrapperAdapter }

trait BasicPersistentServiceContracts[TTable, TRow, TKey]
  extends BasicPersistentServiceOperationsImplementation[TTable, TRow, TKey]
    with MultiplePersistentServiceOperationImplementation[TTable, TRow, TKey]
    with RepositoryWrapperAdapter[TTable, TRow, TKey]
    with RepositoryJsonAdapter[TTable, TRow, TKey]
    with BasicPersistentServiceAdapterOperationsImplementation[TTable, TRow, TKey]
    with BasicPersistentServiceCore[TTable, TRow, TKey]