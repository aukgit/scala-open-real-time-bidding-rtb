package controllers.webapi.core.traits

import shared.com.ortb.model.wrappers.http.ControllerGenericActionWrapper

trait RestWebApiMessages[TTable, TRow, TKey] {
  val entityCreateFailedMessage = "Entity failed to create"
  val entityCreateSuccessMessage = "Entity create successful"

  def getDefaultFailedMessage(
    controllerGenericActionWrapper : Option[ControllerGenericActionWrapper] = None,
    entity : Option[TRow] = None,
    message                        : String = "") : String

  def getDefaultSuccessMessage(
    controllerGenericActionWrapper : Option[ControllerGenericActionWrapper] = None,
    entity                         : Option[TRow] = None,
    message                        : String = "") : String
}
