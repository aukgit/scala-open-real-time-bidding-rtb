package controllers.rtb.traits

import play.api.mvc.Result

trait RestWebApiOkJson {
  def OkJson(jsonString : String) : Result
}
