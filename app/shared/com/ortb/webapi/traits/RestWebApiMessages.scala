package shared.com.ortb.webapi.traits

import play.api.mvc.Headers
import shared.com.ortb.enumeration.DatabaseActionType.DatabaseActionType
import shared.com.ortb.enumeration.HttpMethodType.HttpMethodType

trait RestWebApiMessages[TTable, TRow, TKey] {
  def failedMessage(item : Option[TRow] = None, additionalMessage : String = "") : String

  def successMessage(item : Option[TRow] = None, additionalMessage : String = "") : String

  def performBadRequest(
    entity            : Option[TRow],
    message           : String,
    rawBody           : String,
    databaseActionType : DatabaseActionType,
    httpMethodType : HttpMethodType,
    headers : Option[Headers] = None
  )

  def performOkayOnEntity(
    entity : Option[TRow],
    message : String,
    httpMethodType : HttpMethodType,
    databaseActionType : DatabaseActionType,
    headers : Option[Headers] = None
  )

  def performOkay(
    message : String,
    httpMethodType : HttpMethodType,
    headers : Option[Headers] = None
  )
}
