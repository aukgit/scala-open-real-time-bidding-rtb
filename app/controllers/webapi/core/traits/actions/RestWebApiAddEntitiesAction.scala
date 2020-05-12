package controllers.webapi.core.traits.actions

import play.api.mvc.{ Action, AnyContent }

trait RestWebApiAddEntitiesAction[TTable, TRow, TKey] {
  def addEntities() : Action[AnyContent]

  def addEntitiesBySinge(addTimes : Int) : Action[AnyContent]
}
