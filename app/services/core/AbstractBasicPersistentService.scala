package services.core

import services.core.implementations.ServiceEncodersImplementation
import services.core.traits._
import shared.com.ortb.manager.AppManager

abstract class AbstractBasicPersistentService[TTable, TRow, TKey]
  extends
    BasicPersistentServiceContracts[TTable, TRow, TKey] {
  lazy override val appManager : AppManager = repository.appManagerInstance
  lazy override val serviceEncoders : ServiceEncoders[TTable, TRow, TKey] =
    new ServiceEncodersImplementation[TTable, TRow, TKey](this)
}
