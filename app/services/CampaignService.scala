package services

import com.google.inject.Inject
import services.traits.BasicPersistentServiceOperations
import shared.com.ortb.manager.AppManager
import shared.com.ortb.persistent.repositories.CampaignRepository
import shared.com.ortb.persistent.repositories.pattern.RepositoryBase
import shared.com.ortb.persistent.schema.Tables._

class CampaignService @Inject()(
  appManager: AppManager,
  campaignRepository : CampaignRepository)
  extends BasicPersistentServiceOperations[Campaign, CampaignRow, Int] {

  val repository : RepositoryBase[Campaign, CampaignRow, Int] =
    campaignRepository
}
