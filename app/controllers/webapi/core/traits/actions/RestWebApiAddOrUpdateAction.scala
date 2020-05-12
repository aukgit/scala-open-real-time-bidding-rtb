package controllers.webapi.core.traits.actions

import play.api.mvc.{ Action, AnyContent }

trait RestWebApiAddOrUpdateAction[TTable, TRow, TKey] {
  def addOrUpdate(id : TKey) : Action[AnyContent]
}
