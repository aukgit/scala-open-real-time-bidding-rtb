package services

import com.google.inject.Inject
import services.traits.ServiceBasicPersistentOperations
import shared.com.ortb.persistent.repositories.CampaignRepository
import shared.com.ortb.persistent.repositories.pattern.RepositoryBase
import shared.com.ortb.persistent.schema.Tables._

class CampaignService @Inject()(
  campaignRepository                               : CampaignRepository)
  extends ServiceBasicPersistentOperations[Campaign, CampaignRow, Int] {

  val repository : RepositoryBase[Campaign, CampaignRow, Int] =
    campaignRepository
}
