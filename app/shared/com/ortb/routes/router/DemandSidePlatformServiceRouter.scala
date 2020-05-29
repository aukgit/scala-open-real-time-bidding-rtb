package shared.com.ortb.routes.router

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
    //noinspection DuplicatedCode
    try {
      case GET(p"/serviceName") | GET(p"/service-name") | GET(p"/") | POST(p"/") | HEAD(p"/") =>
        controller.getServiceName()
      case POST(p"/makeBidRequest") | POST(p"/make-bid-request") =>
        controller.makeBidRequest()
      case POST(p"/commands") | GET(p"/available-commands") =>
        controller.getAvailableCommands()
    } catch {
      case e : Exception =>
        controller.handleError(e, routingActionWrapper)
        throw e
    }
  }
}
