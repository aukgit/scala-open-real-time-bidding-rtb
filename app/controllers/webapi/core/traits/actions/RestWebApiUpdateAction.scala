package controllers.webapi.core.traits.actions

import play.api.mvc.{ Action, AnyContent }

trait RestWebApiUpdateAction[TTable, TRow, TKey] {
  def update(id : TKey) : Action[AnyContent]
}
