package shared.io.helpers

import controllers.webapi.core.implementations.GenericControllerResponseImplementation
import controllers.webapi.core.traits.GenericControllerResponse
import shared.io.helpers.traits.ResponseHelperBase

object ResponseHelper extends ResponseHelperBase {
  lazy override val genericControllerResponse : GenericControllerResponse = new GenericControllerResponseImplementation
}
