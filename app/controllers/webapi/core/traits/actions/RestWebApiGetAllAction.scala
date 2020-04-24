package controllers.webapi.core.traits.actions

import play.api.mvc.{ Action, _ }

trait RestWebApiGetAllAction[TTable, TRow, TKey] {
  def getAll : Action[AnyContent]
}
