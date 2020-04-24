package services

import com.google.inject.Inject
import services.core.AbstractBasicPersistentService
import shared.com.ortb.persistent.repositories._
import shared.com.ortb.persistent.schema.Tables._
import shared.com.repository.RepositoryBase

class BidResponseService @Inject()(
  injectedRepository : BidResponseRepository)
  extends AbstractBasicPersistentService[Bidresponse, BidresponseRow, Int] {

  val repository : RepositoryBase[Bidresponse, BidresponseRow, Int] =
    injectedRepository
}
