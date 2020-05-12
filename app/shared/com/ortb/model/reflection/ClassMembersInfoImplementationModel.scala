package shared.com.ortb.model.reflection

import shared.com.ortb.model.reflection.traits.{ ClassMembersInfoBaseModel, ClassMembersInfoContractsModel }

import scala.collection.Map
import scala.collection.mutable.ArrayBuffer
import scala.concurrent.Future

case class ClassMembersInfoImplementationModel(
  classType : Class[_],
  eventualFields : Future[Map[String, ArrayBuffer[FieldWrapperModel]]],
  eventualMethods : Future[Map[String, ArrayBuffer[MethodWrapperModel]]],
  eventualConstructors : Future[Map[Int, ArrayBuffer[ConstructorWrapperModel]]]
) extends ClassMembersInfoBaseModel with ClassMembersInfoContractsModel {
  lazy override val classMembersInfo : ClassMembersInfoBaseModel = this
}
