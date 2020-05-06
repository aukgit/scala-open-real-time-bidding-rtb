package shared.com.ortb.constants

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
  lazy val NewLine : String = System.lineSeparator

  lazy val DoubleSpace : String = "  "

  lazy val Space2 : String = DoubleSpace

  lazy val Space4 : String = s"$DoubleSpace${DoubleSpace}"

  /**
   * "=${NewLine}${DoubleSpace}"
   */
  lazy val LogEqualNewLineWithIndent : String = s"=${NewLine}${DoubleSpace}"
}
