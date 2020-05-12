package services

import com.google.inject.Inject
import services.core.AbstractBasicPersistentService
import shared.com.ortb.persistent.repositories._
import shared.com.ortb.persistent.schema.Tables._

class CampaignTargetOperatingSystemService @Inject()(
  injectedRepository : CampaignTargetOperatingSystemRepository)
  extends AbstractBasicPersistentService[Campaigntargetoperatingsystem, CampaigntargetoperatingsystemRow, Int] {

  val repository = injectedRepository
}
