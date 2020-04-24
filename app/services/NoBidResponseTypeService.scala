package services

import com.google.inject.Inject
import services.core.AbstractBasicPersistentService
import shared.com.ortb.persistent.repositories._
import shared.com.ortb.persistent.schema.Tables._
import shared.com.repository.RepositoryBase

class NoBidResponseTypeService @Inject()(
  injectedRepository : NoBidResponseTypeRepository)
  extends AbstractBasicPersistentService[Nobidresponsetype, NobidresponsetypeRow, Int] {

  val repository = injectedRepository
}
