package shared.com.ortb.routes.router.core

import com.google.inject.Inject
import controllers.rtb.RequestSimulatorServiceApiController
import play.api.routing.Router.Routes
import play.api.routing.SimpleRouter
import play.api.routing.sird.{ GET, _ }
import shared.com.ortb.enumeration.ControllerDefaultActionType
import shared.com.ortb.model.wrappers.http.ControllerGenericActionWrapper

class RtbServiceBasicRouter @Inject()(
  controller : RequestSimulatorServiceApiController)
  extends SimpleRouter {

  lazy val routingActionWrapper : ControllerGenericActionWrapper = ControllerGenericActionWrapper(
    ControllerDefaultActionType.Routing)

  override def routes : Routes = {
    //noinspection DuplicatedCode
    try {
      case GET(p"/serviceName") | GET(p"/service-name") | GET(p"/") | POST(p"/") | HEAD(p"/") =>
        controller.getServiceName()
      case GET(p"/commands") | GET(p"/available-commands") | GET(p"/routes") =>
        controller.getAvailableCommands()
      case GET(p"/banner-json") =>
        controller.getBannerRequestSample("banner-json")
      case GET(p"/banner-minmax") =>
        controller.getBannerRequestSample("banner-minmax")
      case GET(p"/banner-minmax-mimes") =>
        controller.getBannerRequestSample("banner-minmax")
      case GET(p"/geo-request") =>
        controller.getBannerRequestSample("geo-request")
      case GET(p"/banner-wmax") =>
        controller.getBannerRequestSample("banner-wmax")
      case GET(p"/banner-hmax") =>
        controller.getBannerRequestSample("banner-hmax")
      case GET(p"/banner-hmax-min") =>
        controller.getBannerRequestSample("banner-hmax-min")
      case GET(p"/content-context") =>
        controller.getBannerRequestSample("content-context")

    } catch {
      case e : Exception =>
        controller.handleError(e, routingActionWrapper)
        throw e
    }
  }
}
