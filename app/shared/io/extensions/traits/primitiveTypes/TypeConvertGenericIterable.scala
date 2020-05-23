package shared.io.extensions.traits.primitiveTypes

import shared.com.ortb.constants.AppConstants
import shared.com.ortb.manager.AppManager
import shared.com.ortb.manager.traits.{ CreateDefaultContext, DefaultExecutionContextManager }
import shared.com.ortb.persistent.repositories.LogTraceRepository
import shared.io.helpers.EmptyValidateHelper

import scala.concurrent.ExecutionContext

trait TypeConvertGenericIterable[T]
  extends TypeConvertGenericIterableJson[T] with CreateDefaultContext {
  protected val anyItems : Iterable[T]

  lazy val isEmpty : Boolean = !hasItem
  lazy val hasItem : Boolean = EmptyValidateHelper.hasAnyItemDirect(anyItems)
  lazy val toSome : Option[Iterable[T]] = Some(anyItems)
  lazy val toOption : Option[Iterable[T]] = Some(anyItems)
  lazy val toMaybe : Option[Iterable[T]] = Some(anyItems)
  lazy val toCsv : String = anyItems.mkString(AppConstants.Comma)
  lazy protected val appManager : AppManager = AppConstants.AppManager
  lazy protected val logTraceRepository : LogTraceRepository = AppConstants.Repositories.logTraceRepository

  def toJoinString(separator : String) : String = anyItems.mkString(separator)

  def toJoinString(start : String, separator : String, end : String) : String =
    anyItems.mkString(start, separator, end)

  protected def throwOnNullOrNoneOrNil() : Unit = {
    EmptyValidateHelper.throwOnNullOrNoneOrNil(
      anyItems,
      Some(s"Undefined object given for json parsing."))
  }

  override def createDefaultContext() : ExecutionContext = appManager
    .executionContextManager
    .createDefaultContext()
}
