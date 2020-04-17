package shared.com.ortb.webapi.traits

import play.api.mvc.{Action, AnyContent}

trait RestWebApi[TTable, TRow, TKey] {
  def getAll : Action[AnyContent]

  def byId(id : TKey) : Action[AnyContent]

  def add() : Action[AnyContent]

  def addOrUpdate(id : TKey) : Action[AnyContent]

  def update(id : TKey) : Action[AnyContent]

  def delete(id    : TKey) : Action[AnyContent]

  def addEntities() : Action[AnyContent]

  def addEntitiesBySinge() : Action[AnyContent]
}
