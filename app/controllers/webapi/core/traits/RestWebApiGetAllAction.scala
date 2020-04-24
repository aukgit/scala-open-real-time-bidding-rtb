package controllers.webapi.core.traits

trait RestWebApiGetAllAction[TTable, TRow, TKey] {
  def getAll : Action[AnyContent]
}
