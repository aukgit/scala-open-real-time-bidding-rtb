package shared.io.helpers.traits

import shared.com.ortb.model.results.ResultModel
import shared.com.repository.traits.FutureToRegular
import shared.io.helpers.EmptyValidateHelper

import scala.concurrent.Future

trait ExtractHelperBase {
  def getFromEventual[T](item : Future[Option[T]]) : T = {
    val maybeT = FutureToRegular.toRegular(item)
    get(maybeT)
  }

  def get[T](item : Option[T]) : T = {
    if (EmptyValidateHelper.isEmpty(item)) {
      return null.asInstanceOf[T]
    }

    item.get
  }

  def getFromEventualResult[T](eventualResult : Future[ResultModel[T]]) : T = {
    val result = FutureToRegular.toRegular(eventualResult)

    getFromOptionResult(Some(result))
  }

  def getFromResult[T](item : ResultModel[T]) : T = {
    getFromOptionResult(Some(item))
  }

  def getFromOptionResult[T](item : Option[ResultModel[T]]) : T = {
    if (EmptyValidateHelper.isEmpty(item) ||
      EmptyValidateHelper.isEmpty(item.get.result)) {
      return null.asInstanceOf[T]
    }

    item.get.result.get
  }
}


