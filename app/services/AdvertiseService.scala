package services

import com.google.inject.Inject
import services.core.AbstractBasicPersistentService
import shared.com.ortb.persistent.repositories._
import shared.com.ortb.persistent.schema.Tables._
import shared.com.repository.RepositoryBase

class AdvertiseService @Inject()(
  injectedRepository : AdvertiseRepository)
  extends AbstractBasicPersistentService[Advertise, AdvertiseRow, Int] {

  val repository : RepositoryBase[Advertise, AdvertiseRow, Int] =
    injectedRepository
}
