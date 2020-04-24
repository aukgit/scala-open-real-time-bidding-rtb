package controllers.v1.apis

import controllers.webapi.core.AbstractRestWebApi
import javax.inject.Inject
import play.api.mvc._
import services._
import shared.com.ortb.persistent.schema.Tables._

class CampaignTargetSiteSystemApiController @Inject()(
  injectedService : CampaignTargetSiteService,
  components      : ControllerComponents)
  extends AbstractRestWebApi[Campaigntargetsite, CampaigntargetsiteRow, Int](components) {

  override val service = injectedService
}
