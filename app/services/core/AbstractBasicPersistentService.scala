package services.core

import services.core.traits.BasicPersistentServiceOperations

abstract class AbstractBasicPersistentService[TTable, TRow, TKey]
  extends
    BasicPersistentServiceOperations[TTable, TRow, TKey]
