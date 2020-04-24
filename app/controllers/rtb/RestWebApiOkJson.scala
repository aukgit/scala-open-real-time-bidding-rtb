package controllers.rtb

import play.api.mvc.Result

trait RestWebApiOkJson {
  def OkJson(jsonString : String) : Result
}
