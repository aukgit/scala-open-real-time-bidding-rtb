package services

import com.google.inject.Inject
import services.core.AbstractBasicPersistentService
import shared.com.ortb.persistent.repositories._
import shared.com.ortb.persistent.schema.Tables._
import shared.com.repository.RepositoryBase

class BannerAdvertiseTypeService @Inject()(
  injectedRepository : BannerAdvertiseTypeRepository)
  extends AbstractBasicPersistentService[Banneradvertisetype, BanneradvertisetypeRow, Int] {

  val repository : RepositoryBase[Banneradvertisetype, BanneradvertisetypeRow, Int] =
    injectedRepository
}
