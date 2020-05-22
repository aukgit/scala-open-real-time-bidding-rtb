package shared.com.ortb.routes.traits

import play.api.mvc.{ Action, AnyContent }

trait RouterActionPerformByIds {
  def performGetById(id : String) : Action[AnyContent]

  def performUpdateById(id : String) : Action[AnyContent]
}
