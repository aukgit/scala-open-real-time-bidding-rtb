package shared.io.extensions.traits.genericTypes

import shared.com.repository.traits.FutureToRegular
import shared.io.extensions.TypeConvertExtensions._
import scala.collection.mutable.ArrayBuffer
import scala.concurrent.Future


trait TypeConvertGenericIterableForExtension[T] {
  this : TypeConvertGenericIterable[T] =>

  /**
   * Returns true if any is true, if empty then returns false.
   *
   * @param p
   *
   * @return
   */
  def forAny(p : T => Boolean) : Boolean = {
    if (isEmpty) {
      return false
    }

    val it = anyItems.iterator
    while (it.hasNext) {
      if (p(it.next)) {
        return true
      }
    }

    false
  }

  def forAnyGroup(predicates : (T => Boolean)*) : Array[Boolean] = {
    val predicatesLength = predicates.length
    val array = new Array[Boolean](predicatesLength)

    if (isEmpty) {
      return array
    }

    var trueFound = 0

    for (item <- anyItems) {
      var index = 0

      for (predicate <- predicates) {
        if (!array(index) && predicate(item)) {
          trueFound += 1
          array(index) = true
        }
        index = index + 1
      }

      if (trueFound == predicatesLength) {
        return array
      }
    }

    array
  }
}
