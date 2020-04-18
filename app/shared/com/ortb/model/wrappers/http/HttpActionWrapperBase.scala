package shared.com.ortb.model.wrappers.http

import play.api.mvc.Headers
import shared.com.ortb.enumeration.DatabaseActionType.DatabaseActionType
import shared.com.ortb.enumeration.HttpActionWrapperType.HttpActionWrapperType
import shared.com.ortb.model.wrappers.persistent.{EntityWrapper, EntityWrapperWithOptions}

abstract class HttpActionWrapperBase[TRow, TKey](
  methodName         : Option[String] = None,
  lineNumber         : Option[Int] = None,
  resultType         : Option[HttpActionWrapperType] = None,
  entityWrapper      : Option[EntityWrapperWithOptions[TRow, TKey]] = None,
  rawBodyRequest     : Option[String] = None,
  databaseActionType : Option[DatabaseActionType] = None,
  headers            : Option[Seq[Headers]] = None
)
