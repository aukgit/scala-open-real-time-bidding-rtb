package controllers.controllerRoutes.routerGeneric

import com.google.inject.Inject
import controllers.controllerRoutes.traits.RouterActionPerformByIds
import controllers.rtb.RequestSimulatorServiceApiController
import play.api.routing.Router.Routes
import play.api.routing.SimpleRouter
import play.api.routing.sird._
import shared.com.ortb.enumeration.ControllerDefaultActionType
import shared.com.ortb.model.wrappers.http.ControllerGenericActionWrapper

import scala.reflect.runtime.universe._

class RtbServiceBasicRouter @Inject()(
  controller : RequestSimulatorServiceApiController)
  extends SimpleRouter {

  val routingActionWrapper : ControllerGenericActionWrapper = ControllerGenericActionWrapper(
    ControllerDefaultActionType.Routing)

  override def routes : Routes = {
    try {
      case GET(p"/serviceName") | GET(p"/") =>
        controller.getServiceName()

      case GET(p"/commands") | GET(p"/available-commands") | GET(p"/routes") =>
        controller.getAvailableCommands()
      case GET(p"/bannerRequest") =>
        controller.getBannerRequestSample()
    } catch {
      case e : Exception =>
        controller.handleError(e, routingActionWrapper)
        throw e
    }
  }
}
