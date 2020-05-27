package shared.io.extensions.traits.asyncTypes

import shared.io.extensions.TypeConvertExtensions._
import shared.com.repository.traits.FutureToRegular
import shared.io.helpers.EmptyValidateHelper

import scala.concurrent.Future
import scala.concurrent.duration.Duration
import scala.concurrent.duration.Duration.Inf

trait TypeConvertGenericFuture[T] {
  val eventualRequest : Future[T]

  def toRegular(wait : Duration = Inf) : T = FutureToRegular.toRegular(eventualRequest, wait)

  def toRegularOption(wait : Duration.Infinite = Inf) : Option[T] = FutureToRegular.toRegularAsOption(eventualRequest, wait)
}


