package controllers.controllerRoutes

import controllers.apis.CampaignsApiController
import javax.inject.Inject
import shared.com.ortb.persistent.schema.Tables._

class CampaignApiRouter @Inject()(
  campaignsApiController : CampaignsApiController) extends
  AbstractGenericRouter[Campaign, CampaignRow, Int](campaignsApiController)