package controllers.webapi.core.traits.actions

import play.api.mvc.{ Action, AnyContent }

trait RestWebApiAddAction[TTable, TRow, TKey] {
  def add() : Action[AnyContent]
}
