package controllers.rtb.core

import shared.com.ortb.webapi.core.traits.implementations.RestWebApiMessagesImplementation
import javax.inject.Inject
import play.api.Logger
import play.api.mvc.{ AbstractController, ControllerComponents }
import shared.com.ortb.manager.AppManager
import shared.com.ortb.model.config.ServiceModel
import shared.com.ortb.persistent.Repositories

class ServiceBaseApiController @Inject()(
  val repositories : Repositories,
  val appManager   : AppManager,
  components       : ControllerComponents)
  extends AbstractController(components)
    with RestWebApiMessagesImplementation[ServiceModel] {
  lazy val logger : Logger = Logger(this.getClass)
}
