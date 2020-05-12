package shared.io.helpers.traits

import shared.io.helpers.implementation.HashMapWithArrayBufferAdderConcreteImplementation

trait MapHelperBase {
  lazy val hashMapWithArrayBufferAdder : HashMapWithArrayBufferAdder = new HashMapWithArrayBufferAdderConcreteImplementation
}
