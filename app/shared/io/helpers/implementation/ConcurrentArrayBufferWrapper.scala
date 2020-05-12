package shared.io.helpers.implementation

import scala.collection.immutable
import scala.collection.mutable.ArrayBuffer

class ConcurrentArrayBufferWrapper[T](ab : ArrayBuffer[T]) {
  def addOne(item : T) : Unit = {
    getLockBuffer.addOne(item)
  }

  def addAll(elems : immutable.Iterable[T]) : Unit = {
    //    this.synchronized {
    //      ab.addAll(elems)
    //    }
    getLockBuffer.addAll(elems)
  }

  def getLockBuffer : ArrayBuffer[T] = {
    this.synchronized {
      return ab
    }
  }

  def toIterable : ArrayBuffer[T] = {
    getLockBuffer
  }

  def remove(index : Int, count : Int) : immutable.Iterable[T] = {
    this.synchronized {
      val items = (index until count)
        .map(i => ab(i))
      ab.remove(index, count)

      return items
    }
  }

  def remove(index : Int) : T = {
    this.synchronized {
      val item = ab(index)
      ab.remove(index)

      return item
    }
  }
}
