package controllers.v1.router

import controllers.controllerRoutes.routerGeneric.AbstractGenericRouter
import controllers.v1.apis.CampaignsApiController
import javax.inject.Inject
import shared.com.ortb.persistent.schema.Tables._

class CampaignApiRouter @Inject()(
  campaignsApiController : CampaignsApiController) extends
  AbstractGenericRouter[Campaign, CampaignRow, Int](campaignsApiController)