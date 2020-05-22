package shared.com.ortb.webapi.core.traits.actions

import play.api.mvc.{ Action, _ }

trait RestWebApiDeleteAction[TTable, TRow, TKey] {
  def delete(id : TKey) : Action[AnyContent]
}
