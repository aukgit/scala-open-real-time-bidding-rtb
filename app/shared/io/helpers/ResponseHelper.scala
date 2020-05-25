package shared.io.helpers

import shared.com.ortb.webapi.core.implementations.GenericControllerResponseImplementation
import shared.com.ortb.webapi.core.traits.GenericControllerResponse
import shared.io.helpers.traits.ResponseHelperBase

object ResponseHelper extends ResponseHelperBase {
  lazy override val genericControllerResponse : GenericControllerResponse = new GenericControllerResponseImplementation
}
