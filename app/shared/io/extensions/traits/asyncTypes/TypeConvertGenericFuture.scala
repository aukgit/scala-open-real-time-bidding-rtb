package shared.io.extensions.traits.asyncTypes

import shared.com.repository.traits.FutureToRegular

import scala.concurrent.Future
import scala.concurrent.duration.Duration
import scala.concurrent.duration.Duration.Inf

trait TypeConvertGenericFuture[T] {
  protected val eventualRequest : Future[T]

  def toRegular(wait : Duration = Inf) : T = FutureToRegular.toRegular(eventualRequest, wait)

  def toRegularOption(wait : Duration.Infinite = Inf) : Option[T] = FutureToRegular.toRegularAsOption(eventualRequest, wait)
}


