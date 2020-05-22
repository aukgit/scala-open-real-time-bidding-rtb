package shared.com.ortb.controllers.traits

import play.api.mvc.Result

trait RestWebApiOkJson {
  def OkJson(jsonString : String) : Result
}
