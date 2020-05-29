package shared.com.ortb.controllers.core

import javax.inject.Inject
import play.api.mvc.{ AbstractController, ControllerComponents }
import shared.com.ortb.controllers.traits.ControllerLogger
import shared.com.ortb.manager.AppManager
import shared.com.ortb.model.config.ServiceModel
import shared.com.ortb.webapi.core.traits.implementations.RestWebApiMessagesImplementation

class ServiceCoreApiController @Inject()(
  val appManager : AppManager,
  components : ControllerComponents)
  extends AbstractController(components)
    with RestWebApiMessagesImplementation[ServiceModel]
    with ControllerLogger
