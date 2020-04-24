package services

import com.google.inject.Inject
import services.core.AbstractBasicPersistentService
import shared.com.ortb.persistent.repositories._
import shared.com.ortb.persistent.schema.Tables._

class CampaignTargetSiteService @Inject()(
  injectedRepository : CampaignTargetSiteRepository)
  extends AbstractBasicPersistentService[Campaigntargetsite, CampaigntargetsiteRow, Int] {

  val repository = injectedRepository
}
