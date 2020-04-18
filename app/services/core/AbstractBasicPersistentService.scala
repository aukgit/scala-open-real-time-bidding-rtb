package services.core

import services.core.traits.BasicPersistentServiceContracts

abstract class AbstractBasicPersistentService[TTable, TRow, TKey]
  extends
    BasicPersistentServiceContracts[TTable, TRow, TKey]
