package controllers.controllerRoutes.router

import com.google.inject.Inject
import play.api.routing.Router.Routes
import play.api.routing.SimpleRouter
import play.api.routing.sird.{ GET, _ }
import shared.com.ortb.demadSidePlatforms.DemandSidePlatformSimulatorServiceApiController
import shared.com.ortb.enumeration.ControllerDefaultActionType
import shared.com.ortb.model.wrappers.http.ControllerGenericActionWrapper

class DemandSidePlatformServiceRouter @Inject()(
  controller : DemandSidePlatformSimulatorServiceApiController)
  extends SimpleRouter {

  lazy val routingActionWrapper : ControllerGenericActionWrapper = ControllerGenericActionWrapper(
    ControllerDefaultActionType.Routing)

  override def routes : Routes = {
    try {
      case GET(p"/serviceName") | GET(p"/") | POST(p"/") | HEAD(p"/") =>
        controller.getServiceName()
      case POST(p"/makeBidRequst") =>
        controller.makeBidRequest()

    } catch {
      case e : Exception =>
        controller.handleError(e, routingActionWrapper)
        throw e
    }
  }
}
