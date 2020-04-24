package controllers.apis

import controllers.webapi.core.AbstractRestWebApi
import javax.inject.Inject
import play.api.mvc._
import services.CampaignService
import services.core.AbstractBasicPersistentService
import shared.com.ortb.persistent.schema.Tables._

class CampaignsApiController @Inject()(
  campaignService : CampaignService,
  components      : ControllerComponents)
  extends AbstractRestWebApi[Campaign, CampaignRow, Int](components) {

  override val service : AbstractBasicPersistentService[Campaign, CampaignRow, Int] =
    campaignService
}
