package controllers.v1.apis

import shared.com.ortb.webapi.core.AbstractRestWebApi
import javax.inject.Inject
import play.api.mvc._
import services._
import services.core.AbstractBasicPersistentService
import shared.com.ortb.persistent.schema.Tables._

class CampaignsApiController @Inject()(
  campaignService : CampaignService,
  components : ControllerComponents)
  extends AbstractRestWebApi[Campaign, CampaignRow, Int](components) {

  override val service : AbstractBasicPersistentService[Campaign, CampaignRow, Int] =
    campaignService
}


