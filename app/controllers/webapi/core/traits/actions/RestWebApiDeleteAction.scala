package controllers.webapi.core.traits.actions

trait RestWebApiDeleteAction[TTable, TRow, TKey] {
  def delete(id : TKey) : Action[AnyContent]
}
