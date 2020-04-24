package services

import com.google.inject.Inject
import services.core.AbstractBasicPersistentService
import shared.com.ortb.persistent.repositories._
import shared.com.ortb.persistent.schema.Tables._

class CampaignTargetCityService @Inject()(
  injectedRepository : CampaignTargetCityRepository)
  extends AbstractBasicPersistentService[Campaigntargetcity, CampaigntargetcityRow, Int] {

  val repository = injectedRepository
}
