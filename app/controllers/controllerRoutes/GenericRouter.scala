package controllers.controllerRoutes

import controllers.apis.CampaignsApiController
import javax.inject.Inject
import play.api.routing.Router.Routes
import play.api.routing.SimpleRouter
import play.api.routing.sird._
import shared.com.ortb.enumeration.ControllerDefaultActionType
import shared.com.ortb.model.wrappers.http.{ ControllerGenericActionWrapper, HttpFailedActionWrapper }
import shared.io.helpers.NumberHelper

/**
 * Routes and URLs to the PostResource controller.
 */
class GenericRouter @Inject()(
  controller : CampaignsApiController) extends SimpleRouter {
  val prefix = "admin/v1/campaigns"
  val routingActionWrapper : ControllerGenericActionWrapper = ControllerGenericActionWrapper(
    ControllerDefaultActionType.Routing)

  override def routes : Routes = {
    try {

      case GET(p"/") =>
        controller.getAll

      case POST(p"/create") =>
        controller.add()

      case POST(p"/createMany") =>
        controller.addEntities()

//      case PUT(p"/updateMany") =>
//        controller.updat()

      case GET(p"/$id") =>
        val idAsInt = NumberHelper.isInt(id)
        if(idAsInt.isSuccess){
          controller.byId(idAsInt.result.get)
        }
        else {
          val httpFailedActionWrapper = controller.createHttpFailedActionWrapper(
            s"Couldn't convert $id to integer.",
            actionWrapper = routingActionWrapper
          )
          controller.performBadRequestAsAction(
            Some(httpFailedActionWrapper))

        }
      case PUT(p"/$id") =>
        controller.update(id.toInt)
    } catch {
      case e : Exception =>
        controller.handleError(e, routingActionWrapper)
        throw e
    }
  }
}
