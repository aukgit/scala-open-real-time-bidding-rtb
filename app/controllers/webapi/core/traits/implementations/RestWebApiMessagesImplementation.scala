package controllers.webapi.core.traits.implementations

import controllers.webapi.core.traits.RestWebApiMessages
import shared.com.ortb.model.wrappers.http.ControllerGenericActionWrapper

trait RestWebApiMessagesImplementation[TTable, TRow, TKey]  extends RestWebApiMessages[TTable, TRow, TKey]{
  override def getDefaultFailedMessage(
    controllerGenericActionWrapper : Option[ControllerGenericActionWrapper] = None,
    entity : Option[TRow] = None,
    message : String = "") : String = "Failed Operation" // TODO Enhance

  override def getDefaultSuccessMessage(
    controllerGenericActionWrapper : Option[ControllerGenericActionWrapper] = None,
    entity : Option[TRow] = None,
    message : String = "") : String = "Success Operation" // TODO Enhance
}
