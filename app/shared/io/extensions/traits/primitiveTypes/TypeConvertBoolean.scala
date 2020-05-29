package shared.io.extensions.traits.primitiveTypes

import shared.io.extensions.TypeConvertExtensions._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.collection.mutable.ArrayBuffer
import scala.concurrent.Future

trait TypeConvertBoolean {
  protected val b : Boolean

  lazy val toIntString : String = toBoolInt.toString

  lazy val toBoolInt : Int = if (b) 1 else 0

  lazy val toBoolString : String = if (b) "true" else "false"

  lazy val toBoolIntSome : Option[Int] = Some(toBoolInt)

  def doOrElse[T, T2](doOnTrue : () => T, doOnFalse : () => T2 = null) : Either[T2, T] = {
    if (b) {
      return Right(doOnTrue())
    }
    else if (doOnFalse != null) {
      return Left(doOnFalse())
    }

    null
  }

  def doOrElseAsync[T, T2](doOnTrue : () => T, doOnFalse : () => T2 = null) : Either[Future[T2], Future[T]] = {
    if (b) {
      return Right(Future {
        doOnTrue()
      })
    }
    else if (doOnFalse != null) {
      return Left(Future {
        doOnFalse()
      })
    }

    null
  }

  def dosOnTrue[T, T2](functions : (() => Unit)*) : Unit = {
    if (b) {
      functions.foreach(function => function())
    }
  }

  def dosAsyncOnTrue[T, T2](functions : (() => Unit)*) : ArrayBuffer[Future[Unit]] = {
    if (b) {
      return functions.forEachAsync(function => function())
    }

    ArrayBuffer.empty
  }

  def dosAsyncCompleteOnTrue[T, T2](functions : (() => Unit)*) : Unit = {
    if (b) {
      functions.forEachAsyncCompleted(function => function())
    }
  }
}
