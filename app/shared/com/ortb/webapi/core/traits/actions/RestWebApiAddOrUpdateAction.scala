package shared.com.ortb.webapi.core.traits.actions

import play.api.mvc.{ Action, AnyContent }

trait RestWebApiAddOrUpdateAction[TTable, TRow, TKey] {
  def addOrUpdate(id : TKey) : Action[AnyContent]
}
