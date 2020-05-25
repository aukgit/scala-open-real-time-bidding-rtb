package shared.com.ortb.webapi.core.traits.implementations

import shared.com.ortb.webapi.core.traits.RestWebApiMessages
import shared.com.ortb.model.wrappers.http.ControllerGenericActionWrapper

trait RestWebApiMessagesImplementation[TRow] extends RestWebApiMessages[TRow] {
  override def getDefaultFailedMessage(
    controllerGenericActionWrapper : Option[ControllerGenericActionWrapper] = None,
    entity : Option[TRow] = None,
    message : String = "") : String = "Failed Operation" // TODO Enhance

  override def getDefaultSuccessMessage(
    controllerGenericActionWrapper : Option[ControllerGenericActionWrapper] = None,
    entity : Option[TRow] = None,
    message : String = "") : String = "Success Operation" // TODO Enhance
}
