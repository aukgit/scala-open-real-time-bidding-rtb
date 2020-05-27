package shared.io.extensions.traits.asyncTypes

import shared.io.extensions.TypeConvertExtensions._
import shared.io.extensions.traits.genericTypes.TypeConvertGenericIterable

import scala.collection.mutable.ArrayBuffer
import scala.concurrent.Future

trait TypeConvertGenericIterableForAsyncExtension[T] {
  this : TypeConvertGenericIterable[T] =>
  def forEachAsyncCompleted(f : T => Unit, defaultCapacity : Int = 50) : Unit = {
    val allFutures = forEachAsync(f, defaultCapacity)
    allFutures.waitUntilComplete()
  }

  def forEachAsync(f : T => Unit, defaultCapacity : Int = 50) : ArrayBuffer[Future[Unit]] = {
    if (isEmpty) {
      return ArrayBuffer.empty
    }

    val arrayBuffer = new ArrayBuffer[Future[Unit]](defaultCapacity)
    for (item <- anyItems) {
      val future = Future {
        f(item)
      }

      arrayBuffer.addOne(future)
    }

    arrayBuffer
  }
}
