package controllers.webapi.core.traits

trait RestWebApiAddOrUpdateAction[TTable, TRow, TKey] {
  def addOrUpdate(id : TKey) : Action[AnyContent]
}
