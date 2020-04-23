package shared.io.helpers

import shared.com.ortb.webapi.GenericControllerResponseImplementation
import shared.com.ortb.webapi.traits.GenericControllerResponse
import shared.io.helpers.traits.ResponseHelperBase

object ResponseHelper extends ResponseHelperBase {
  lazy override val genericControllerResponse : GenericControllerResponse = new GenericControllerResponseImplementation
}
