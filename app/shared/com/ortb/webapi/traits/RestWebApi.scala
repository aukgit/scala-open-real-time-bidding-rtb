package shared.com.ortb.webapi.traits

import play.api.mvc.{Action, AnyContent}

trait RestWebApi[TTable, TRow, TKey] {
  def getAll : Action[AnyContent]

  /**
   * Http Get method executes this.
   * @param id
   * @return
   */
  def byId(id : TKey) : Action[AnyContent]

  /**
   * Http Post method executes this.
   * @return
   */
  def add() : Action[AnyContent]

  def addOrUpdate(id : TKey) : Action[AnyContent]

  /**
   * HTTP Put updates record
   * @param id
   * @return
   */
  def update(id : TKey) : Action[AnyContent]

  def delete(id    : TKey) : Action[AnyContent]

  def addEntities() : Action[AnyContent]

  def addEntitiesBySinge(addTimes: Int) : Action[AnyContent]
}
