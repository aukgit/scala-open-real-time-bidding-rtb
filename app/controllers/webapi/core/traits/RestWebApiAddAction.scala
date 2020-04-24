package controllers.webapi.core.traits

import play.api.mvc.{ Action, AnyContent }

trait RestWebApiAddAction[TTable, TRow, TKey] {
  def add() : Action[AnyContent]
}

trait RestWebApiAddEntitiesAction[TTable, TRow, TKey] {
  def addEntities() : Action[AnyContent]

  def addEntitiesBySinge(addTimes : Int) : Action[AnyContent]
}
