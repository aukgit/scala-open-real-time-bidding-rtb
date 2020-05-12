package shared.com.ortb.model.reflection

import shared.com.ortb.model.reflection.traits.{ ClassMembersInfoBaseModel, ClassMembersInfoContractsModel }
import shared.com.ortb.model.results.ResultWithCountSuccessModel

import scala.collection._
import scala.collection.mutable.ArrayBuffer
import scala.concurrent.Future

case class ClassMembersInfoModel(
  classType : Class[_],
  eventualFields : Future[Map[String, ArrayBuffer[FieldWrapperModel]]],
  eventualMethods : Future[Map[String, ArrayBuffer[MethodWrapperModel]]],
  eventualConstructors : Future[Map[Int, ArrayBuffer[ConstructorWrapperModel]]],
  eventualMembers : Future[ResultWithCountSuccessModel[scala.Array[MemberWrapperBaseModel]]])
  extends ClassMembersInfoBaseModel
    with ClassMembersInfoContractsModel {
  lazy override val classMembersInfo : ClassMembersInfoBaseModel = this
}
