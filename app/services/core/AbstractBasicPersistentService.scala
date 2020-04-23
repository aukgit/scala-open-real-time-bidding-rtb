package services.core

import services.core.traits.{ BasicPersistentServiceContracts, ServiceEncoders, ServiceEncodersImplementation }

abstract class AbstractBasicPersistentService[TTable, TRow, TKey]
  extends
    BasicPersistentServiceContracts[TTable, TRow, TKey] {
  override val serviceEncoders : ServiceEncoders[TTable, TRow, TKey] =
    new ServiceEncodersImplementation[TTable, TRow, TKey](this)
}
