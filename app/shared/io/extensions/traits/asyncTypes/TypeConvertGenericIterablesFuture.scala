package shared.io.extensions.traits.asyncTypes

import shared.io.extensions.TypeConvertExtensions._
import shared.io.helpers.EmptyValidateHelper

import scala.concurrent.Future
import scala.concurrent.duration.Duration
import scala.concurrent.duration.Duration.Inf

trait TypeConvertGenericIterablesFuture[T] {
  lazy val isEmpty : Boolean = EmptyValidateHelper.isItemsEmptyDirect(eventualRequests)
  protected val eventualRequests : Iterable[Future[T]]

  def waitUntilCompleted(wait : Duration = Inf) : Unit = {
    if (isEmpty) {
      return
    }

    eventualRequests.foreach(eventualRequest => eventualRequest.toRegular(wait))
  }
}
