package services

import com.google.inject.Inject
import services.core.AbstractBasicPersistentService
import shared.com.ortb.persistent.repositories.CampaignRepository
import shared.com.ortb.persistent.schema.Tables._
import shared.com.repository.RepositoryBase

class CampaignService @Inject()(
  campaignRepository : CampaignRepository)
  extends AbstractBasicPersistentService[Campaign, CampaignRow, Int] {

  val repository : RepositoryBase[Campaign, CampaignRow, Int] =
    campaignRepository
}
