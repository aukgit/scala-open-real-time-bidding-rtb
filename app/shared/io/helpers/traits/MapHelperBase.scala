package shared.io.helpers.traits

import shared.io.helpers.implementations.HashMapWithArrayBufferAdderConcreteImplementation

trait MapHelperBase {
  lazy val hashMapWithArrayBufferAdder : HashMapWithArrayBufferAdder = new HashMapWithArrayBufferAdderConcreteImplementation
}
