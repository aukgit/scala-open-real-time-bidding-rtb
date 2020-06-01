package shared.com.ortb.constants

import shared.com.ortb.manager.AppManager
import shared.com.ortb.persistent.Repositories


object AppConstants {

  /**
   * Dot(.) string.
   */
  lazy val Dot = "."

  /**
   * "configuration.json"
   */
  lazy val DefaultConfigFileNameWithExtension = "configuration.json"

  lazy val PathConstants = new PathConstants

  lazy val QueryStringNameConstants = new QueryStringNameConstants

  lazy val SentryDSN = "https://3540a18396eb4373b3c843b149c55f5d@sentry.io/5183951"

  lazy val DefaultPageSize = 100

  /**
   * ",\n <br >"
   */
  lazy val NewLineForSentry = ",\n <br >"

  /**
   * "-> "
   */
  lazy val HyphenRightAngel = "-> "

  /**
   * "localhost"
   */
  lazy val LocalHost = "localhost"

  lazy val DefaultParallelExecution = 3

  lazy val NoContent = "No content, no operation performed."

  lazy val NoContentInRequest = "No content in request"

  lazy val EmptyString : String = ""

  lazy val CurrencyUsd : String = "USD"

  lazy val EmptyStringOption : Option[String] = Some("")

  lazy val EmptyDoubleOption : Option[Double] = Some(0)

  lazy val EmptyIntegerOption : Option[Int] = Some(0)

  lazy val Quote = "\""

  /**
   * System.lineSeparator
   */
  lazy val SystemNewLine : String = System.lineSeparator

  /**
   * '\n'
   */
  lazy val NewLine : Char = '\n'

  /**
   * '\t'
   */
  lazy val Tab : Char = '\t'

  lazy val DoubleSpace : String = "  "

  lazy val Space2 : String = DoubleSpace

  lazy val Space4 : String = s"$DoubleSpace${ DoubleSpace }"

  lazy val EqualSign = "="

  lazy val Colon = ":"
  lazy val Comma = ","
  lazy val CommaSpace = ", "

  lazy val NewRecordIntId : Int = -1
  lazy val TrueInteger : Int = 1
  lazy val FalseInteger : Int = 0
  lazy val NewRecordStringId : String = ""

  lazy val DefaultDateTimeFormatPattern = "MM/dd/yyyy HH:mm:ss"
  lazy val DefaultDateFormatPattern = "MM/dd/yyyy"
  lazy val IsThrownOnFailed = true

  /**
   * "=${NewLine}"
   */
  lazy val LogEqualNewLine : String = s"${ EqualSign }${ SystemNewLine }"

  /**
   * "=${NewLine}${DoubleSpace}"
   */
  lazy val LogEqualNewLineWithIndent : String = s"${ LogEqualNewLine }${ DoubleSpace }"

  lazy val AppManager : AppManager = new AppManager

  lazy val Repositories : Repositories = new Repositories(AppManager)
  lazy val BiddingConstants = new BiddingConstants

  lazy val BooleanYesString = "yes"
  lazy val BooleanTrueString = "true"
  lazy val BooleanFalseString = "false"

  /**
   * "//"
   */
  lazy val DoubleForwardSlash = "//"


  /**
   * "//"
   */
  lazy val DoubleBackwardSlash = s"$BackwardSlash$BackwardSlash"

  /**
   * "/"
   */
  lazy val ForwardSlash = "/"

  /**
   * "\\"
   */
  lazy val BackwardSlash = "\\"
}
