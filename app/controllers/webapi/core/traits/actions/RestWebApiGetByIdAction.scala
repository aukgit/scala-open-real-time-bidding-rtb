package controllers.webapi.core.traits.actions

import play.api.mvc.{ Action, AnyContent }

trait RestWebApiGetByIdAction[TTable, TRow, TKey] {
  def byId(id : TKey) : Action[AnyContent]
}


