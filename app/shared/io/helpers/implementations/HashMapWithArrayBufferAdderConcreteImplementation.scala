package shared.io.helpers.implementations

import java.util.concurrent.ConcurrentMap

import shared.io.helpers.traits.HashMapWithArrayBufferAdder

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
      hashMap.addOne(key, arrayBuffer)
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
      concurrentMap.put(key, arrayBuffer)
    } else {
      // NOTE: Don't use getOrDefault as it gets evaluated and creates Array buffer on each call.
      arrayBuffer = concurrentMap.get(key)
    }

    this.synchronized {
      arrayBuffer.addOne(value)
    }
  }
}
