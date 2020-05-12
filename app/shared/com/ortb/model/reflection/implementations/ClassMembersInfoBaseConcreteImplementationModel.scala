package shared.com.ortb.model.reflection.implementations

import shared.com.ortb.model.reflection.traits.{ ClassMembersInfoBaseModel, ClassMembersInfoContractsModel }
import shared.com.ortb.model.reflection.{ ConstructorWrapperModel, FieldWrapperModel, MethodWrapperModel }

import scala.collection.Map
import scala.collection.mutable.ArrayBuffer
import scala.concurrent.Future

case class ClassMembersInfoBaseConcreteImplementationModel[T](
  classType : Class[_],
  entity : Option[T],
  eventualFields : Future[Map[String, ArrayBuffer[FieldWrapperModel]]],
  eventualMethods : Future[Map[String, ArrayBuffer[MethodWrapperModel]]],
  eventualConstructors : Future[Map[Int, ArrayBuffer[ConstructorWrapperModel]]]
) extends ClassMembersInfoBaseModel[T] with ClassMembersInfoContractsModel[T] {
  lazy override val classMembersInfo : ClassMembersInfoBaseModel[T] = this
}
