package controllers.webapi.core.traits

trait RestWebApiGetByIdAction[TTable, TRow, TKey] {
  def byId(id : TKey) : Action[AnyContent]
}
