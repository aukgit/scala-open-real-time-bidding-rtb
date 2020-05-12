package shared.com.ortb.model.reflection

import shared.com.ortb.model.reflection.traits.{ ClassMembersInfoBaseModel, ClassMembersInfoContractsModel }
import shared.com.ortb.model.results.ResultWithCountSuccessModel

import scala.collection._
import scala.collection.mutable.ArrayBuffer
import scala.concurrent.Future

case class ClassMembersInfoModel[T](
  classType : Class[_],
  entity : Option[T],
  eventualFields : Future[Map[String, ArrayBuffer[FieldWrapperModel]]],
  eventualMethods : Future[Map[String, ArrayBuffer[MethodWrapperModel]]],
  eventualConstructors : Future[Map[Int, ArrayBuffer[ConstructorWrapperModel]]],
  eventualMembers : Future[ResultWithCountSuccessModel[scala.Array[MemberWrapperBaseModel]]])
  extends ClassMembersInfoBaseModel[T]
    with ClassMembersInfoContractsModel[T] {
  lazy override val classMembersInfo : ClassMembersInfoBaseModel[T] = this
}
