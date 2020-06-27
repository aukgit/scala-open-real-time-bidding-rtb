package shared.com.ortb.constants

import scala.util.matching.Regex

sealed class RegExConstants {
  lazy val routingPlaceHolder = "\\$[\\w+(-)\\d+]*"
  lazy val routingPlaceHolderRegEx : Regex = routingPlaceHolder.r
}
