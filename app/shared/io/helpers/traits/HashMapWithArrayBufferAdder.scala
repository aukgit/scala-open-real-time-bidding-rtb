package shared.io.helpers.traits

import java.util.concurrent.ConcurrentMap

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

trait HashMapWithArrayBufferAdder {
  def addToArrayBuffer[TKey, TValue](
    hashMap : mutable.HashMap[TKey, ArrayBuffer[TValue]],
    key : TKey,
    value : TValue,
    defaultCapacity : Int = 5) : Unit

  def concurrentMapAddToArrayBuffer[TKey, TValue](
    concurrentMap : ConcurrentMap[TKey, ArrayBuffer[TValue]],
    key : TKey,
    value : TValue,
    defaultCapacity : Int = 5) : Unit
}
