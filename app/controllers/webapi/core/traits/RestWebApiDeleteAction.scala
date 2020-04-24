package controllers.webapi.core.traits

trait RestWebApiDeleteAction[TTable, TRow, TKey] {
  def delete(id : TKey) : Action[AnyContent]
}
