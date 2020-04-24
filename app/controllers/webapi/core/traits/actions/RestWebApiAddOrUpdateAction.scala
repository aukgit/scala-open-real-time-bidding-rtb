package controllers.webapi.core.traits.actions

trait RestWebApiAddOrUpdateAction[TTable, TRow, TKey] {
  def addOrUpdate(id : TKey) : Action[AnyContent]
}
