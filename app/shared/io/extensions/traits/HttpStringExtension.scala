package shared.io.extensions.traits

import shared.com.ortb.constants.AppConstants
import shared.io.extensions.traits.primitiveTypes.StringExtensionForExistence
import shared.io.helpers.PathHelper

trait HttpStringExtension extends StringExtensionForExistence {
  lazy val safeNormalizeDoubleForwardSlashToSingle : String = safeString
    .replace(AppConstants.DoubleForwardSlash, AppConstants.ForwardSlash)

  lazy val safeNormalizeDoubleBackwardSlashToSingle : String = safeString
    .replace(AppConstants.DoubleBackwardSlash, AppConstants.BackwardSlash)

  lazy val safeTrimForwardSlashBothEnds : String = safeTrim(AppConstants.ForwardSlash)

  def trim(trimmer : String) : String = s
    .stripPrefix(trimmer)
    .stripSuffix(trimmer)

  def safeTrim(trimmer : String) : String = safeString
    .stripPrefix(trimmer)
    .stripSuffix(trimmer)

  def combineWith(separator : String, paths : String*) : String =
    s"$s${ separator }" + PathHelper.getCombinedPathWithSequence(separator, paths)
}
