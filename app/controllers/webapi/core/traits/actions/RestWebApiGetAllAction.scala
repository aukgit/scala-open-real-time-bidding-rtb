package controllers.webapi.core.traits.actions

trait RestWebApiGetAllAction[TTable, TRow, TKey] {
  def getAll : Action[AnyContent]
}
