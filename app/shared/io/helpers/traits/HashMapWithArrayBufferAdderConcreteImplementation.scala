package shared.io.helpers.traits

import java.util.concurrent.ConcurrentMap

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

class HashMapWithArrayBufferAdderConcreteImplementation extends HashMapWithArrayBufferAdder {
  def addToArrayBuffer[TKey, TValue](
    hashMap : mutable.HashMap[TKey, ArrayBuffer[TValue]],
    key : TKey,
    value : TValue,
    defaultCapacity : Int = 5) : Unit = {
    var arrayBuffer : ArrayBuffer[TValue] = null

    if (!hashMap.contains(key)) {
      arrayBuffer = new ArrayBuffer[TValue](defaultCapacity)
    } else {
      arrayBuffer = hashMap(key)
    }

    arrayBuffer.addOne(value)
  }

  override def concurrentMapAddToArrayBuffer[TKey, TValue](
    concurrentMap : ConcurrentMap[TKey, ArrayBuffer[TValue]],
    key : TKey,
    value : TValue,
    defaultCapacity : Int) : Unit = {
    var arrayBuffer : ArrayBuffer[TValue] = null

    if (!concurrentMap.containsKey(key)) {
      arrayBuffer = new ArrayBuffer[TValue](defaultCapacity)
    } else {
      // NOTE: Don't use getOrDefault as it gets evaluated and creates Array buffer on each call.
      arrayBuffer = concurrentMap.get(key)
    }

    arrayBuffer.addOne(value)
  }
}
