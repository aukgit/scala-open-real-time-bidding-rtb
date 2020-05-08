package shared.io.helpers.traits

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
}
