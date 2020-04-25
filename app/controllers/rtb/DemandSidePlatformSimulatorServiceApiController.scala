package controllers.rtb

import controllers.rtb.core.AbstractBaseSimulatorServiceApiController
import javax.inject.Inject
import play.api.mvc._
import shared.com.ortb.manager.AppManager
import shared.com.ortb.model.config.ServiceModel
import shared.com.ortb.persistent.Repositories

class DemandSidePlatformSimulatorServiceApiController @Inject()(
  repositories : Repositories,
  appManager : AppManager,
  components : ControllerComponents)
  extends AbstractBaseSimulatorServiceApiController(repositories, appManager, components) {
  override val currentServiceModel : ServiceModel = services.demandSidePlatForms(0)

  def makeBid()
}
