package shared.io.helpers.traits

trait MapHelperBase {
  lazy val hashMapWithArrayBufferAdder : HashMapWithArrayBufferAdder = new HashMapWithArrayBufferAdderConcreteImplementation
}
