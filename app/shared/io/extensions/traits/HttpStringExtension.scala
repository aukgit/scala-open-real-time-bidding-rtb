package shared.io.extensions.traits

import akka.http.scaladsl.model._
import shared.com.ortb.constants.AppConstants
import shared.io.extensions.TypeConvertExtensions._
import shared.io.extensions.traits.primitiveTypes.{ StringExtensionEssentials, StringExtensionForExistence }
import shared.io.helpers.PathHelper

import scala.collection.immutable
import scala.concurrent.Future

trait HttpStringExtension extends StringExtensionEssentials {
  lazy val safeNormalizeDoubleForwardSlashToSingle : String = safeString
    .replace(AppConstants.DoubleForwardSlash, AppConstants.ForwardSlash)

  lazy val safeNormalizeDoubleBackwardSlashToSingle : String = safeString
    .replace(AppConstants.DoubleBackwardSlash, AppConstants.BackwardSlash)

  lazy val safeTrimForwardSlashBothEnds : String = safeTrim(AppConstants.ForwardSlash)
  lazy val safeTrimForwardSlashStarting : String = s
    .stripPrefix(AppConstants.ForwardSlash)

  lazy val safeTrimForwardSlashEnding : String = s
    .stripSuffix(AppConstants.ForwardSlash)

  def trim(trimmer : String) : String = s
    .stripPrefix(trimmer)
    .stripSuffix(trimmer)

  def safeTrim(trimmer : String) : String = safeString
    .stripPrefix(trimmer)
    .stripSuffix(trimmer)

  def combineWithForwardSlash(paths : String*) : String =
    combineWithSequence(AppConstants.ForwardSlash, paths)

  private def combineWithSequence(separator : String, paths : Seq[String]) : String =
    s"$s${ separator }" + PathHelper.getCombinedPathWithSequence(separator, paths)

  def combineWithBackwardSlash(paths : String*) : String =
    combineWithSequence(AppConstants.BackwardSlash, paths)

  def combineWith(separator : String, paths : String*) : String =
    combineWithSequence(separator, paths)

  def toBadRequestResponse(
    statusCodes : StatusCode = StatusCodes.BadRequest,
    mimes : ContentType.WithFixedCharset = ContentTypes.`application/json`,
    headers : immutable.Seq[HttpHeader] = Nil) : HttpResponse =
    toHttpJsonResponse(statusCodes, mimes, headers)

  def toBadRequestResponseFuture(
    statusCodes : StatusCode = StatusCodes.BadRequest,
    mimes : ContentType.WithFixedCharset = ContentTypes.`application/json`,
    headers : immutable.Seq[HttpHeader] = Nil) : Future[HttpResponse] =
    toHttpJsonResponseFuture(statusCodes, mimes, headers)

  def toHttpJsonResponseFuture(
    statusCodes : StatusCode = StatusCodes.Accepted,
    mimes : ContentType.WithFixedCharset = ContentTypes.`application/json`,
    headers : immutable.Seq[HttpHeader] = Nil) : Future[HttpResponse] =
    toHttpJsonResponse(statusCodes, mimes, headers).toFuture

  def toHttpJsonResponse(
    statusCodes : StatusCode = StatusCodes.Accepted,
    mimes : ContentType.WithFixedCharset = ContentTypes.`application/json`,
    headers : immutable.Seq[HttpHeader] = Nil) : HttpResponse =
    HttpResponse(
      status = statusCodes,
      headers = headers,
      entity = toHttpEntity(mimes))

  def toHttpEntity(mimes : ContentType.WithFixedCharset = ContentTypes.`application/json`) : ResponseEntity = HttpEntity(
    mimes,
    safeString
  )
}
