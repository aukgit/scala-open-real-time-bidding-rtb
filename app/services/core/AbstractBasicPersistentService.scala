package services.core

import services.core.implementations.ServiceEncodersImplementation
import services.core.traits.{ BasicPersistentServiceContracts, ServiceEncoders }

abstract class AbstractBasicPersistentService[TTable, TRow, TKey]
  extends
    BasicPersistentServiceContracts[TTable, TRow, TKey] {
  override val serviceEncoders : ServiceEncoders[TTable, TRow, TKey] =
    new ServiceEncodersImplementation[TTable, TRow, TKey](this)
}
