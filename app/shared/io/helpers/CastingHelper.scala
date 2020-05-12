package shared.io.helpers

import shared.io.loggers.AppLogger

object CastingHelper {
  def isProduct(item : Any) : Boolean = {
    if(EmptyValidateHelper.isEmptyAny(item)){
      return false
    }

    item.isInstanceOf[Product]
  }

  def toProduct(item : Any) : Option[Product] = {
    val productModel = safeCastAs[Product](item)
    productModel
  }

  def safeCastAs[T](item : Any) : Option[T] = {
    val isEmpty = EmptyValidateHelper.isEmptyAny(item)

    if (isEmpty) {
      return None
    }

    try {
      //noinspection TypeCheckCanBeMatch
      if (item.isInstanceOf[T]) {
        return Some(item.asInstanceOf[T])
      }
    } catch {
      case e : Exception => AppLogger.error(e)
    }

    None
  }

  def safeCastEitherAs[T, T2](item : Any) : Option[Either[T, T2]] = {
    val isEmpty = EmptyValidateHelper.isEmptyAny(item)

    if (isEmpty) {
      return None
    }

    try {
      item match {
        case t : T =>
          return Some(Left(t))
        case t2 : T2 =>
          return Some(Right(t2))
        case _ => return None
      }
    } catch {
      case e : Exception => AppLogger.error(e)
    }

    None
  }
}
