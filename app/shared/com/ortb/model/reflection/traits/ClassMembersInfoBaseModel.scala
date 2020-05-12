package shared.com.ortb.model.reflection.traits

import shared.com.ortb.model.reflection.{ ConstructorWrapperModel, FieldWrapperModel, MethodWrapperModel }

import scala.collection.Map
import scala.collection.mutable.ArrayBuffer
import scala.concurrent.Future

trait ClassMembersInfoBaseModel[T] extends ClassMembersInfoContractsModel[T] {
  val classType : Class[_]
  val entity : Option[T]
  val eventualFields : Future[Map[String, ArrayBuffer[FieldWrapperModel]]]
  val eventualMethods : Future[Map[String, ArrayBuffer[MethodWrapperModel]]]
  val eventualConstructors : Future[Map[Int, ArrayBuffer[ConstructorWrapperModel]]]
}
