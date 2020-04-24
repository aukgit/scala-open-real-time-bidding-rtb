package controllers.v1.apis

import controllers.webapi.core.AbstractRestWebApi
import javax.inject.Inject
import play.api.mvc._
import services.core.AbstractBasicPersistentService
import services.{ AdvertiseService, CampaignService }
import shared.com.ortb.persistent.schema.Tables._

class CampaignsApiController @Inject()(
  campaignService : CampaignService,
  components      : ControllerComponents)
  extends AbstractRestWebApi[Campaign, CampaignRow, Int](components) {

  override val service : AbstractBasicPersistentService[Campaign, CampaignRow, Int] =
    campaignService
}

class AdvertiseApiController @Inject()(
  injectedService : AdvertiseService,
  components      : ControllerComponents)
  extends AbstractRestWebApi[Advertise, AdvertiseRow, Int](components) {

  override val service = injectedService
}