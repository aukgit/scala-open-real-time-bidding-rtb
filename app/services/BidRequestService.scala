package services

import com.google.inject.Inject
import services.core.AbstractBasicPersistentService
import shared.com.ortb.persistent.repositories._
import shared.com.ortb.persistent.schema.Tables._
import shared.com.repository.RepositoryBase

class BidRequestService @Inject()(
  injectedRepository : BidRequestRepository)
  extends AbstractBasicPersistentService[Bidrequest, BidrequestRow, Int] {

  val repository : RepositoryBase[Bidrequest, BidrequestRow, Int] =
    injectedRepository
}
