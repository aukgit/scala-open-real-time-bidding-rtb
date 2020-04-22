package controllers.controllerRoutes

import controllers.apis.CampaignsApiController
import javax.inject.Inject
import play.api.routing.Router.Routes
import play.api.routing.SimpleRouter
import play.api.routing.sird._

/**
  * Routes and URLs to the PostResource controller.
  */
class GenericRouter @Inject()(
  controller : CampaignsApiController) extends SimpleRouter {
  val prefix = "admin/v1/campaigns"

  override def routes: Routes = {
    case GET(p"/") =>
      controller.getAll

    case POST(p"/create") =>
      controller.add()

    case GET(p"/$id") =>
      controller.byId(id.toInt)

    case PUT(p"/$id") =>
      controller.update(id.toInt)
  } 
}
