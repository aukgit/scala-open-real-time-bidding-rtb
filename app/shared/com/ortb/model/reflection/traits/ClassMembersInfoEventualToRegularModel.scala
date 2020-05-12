package shared.com.ortb.model.reflection.traits

import java.lang.reflect.Member

import shared.com.ortb.model.reflection.{ ConstructorWrapperModel, FieldWrapperModel, MemberWrapperBaseModel, MethodWrapperModel }
import shared.com.repository.traits.FutureToRegular
import shared.io.helpers.{ ExtractHelper, ReflectionHelper }

import scala.collection.mutable.ArrayBuffer
import scala.collection.{ Iterable, Map }
import scala.jdk.CollectionConverters._

trait ClassMembersInfoEventualToRegularModel[T] {
  lazy val fields : Map[String, ArrayBuffer[FieldWrapperModel]] =
    FutureToRegular.toRegular(classMembersInfo.eventualFields)
  lazy val methods : Map[String, ArrayBuffer[MethodWrapperModel]] =
    FutureToRegular.toRegular(classMembersInfo.eventualMethods)
  lazy val constructors : Map[Int, ArrayBuffer[ConstructorWrapperModel]] =
    FutureToRegular.toRegular(classMembersInfo.eventualConstructors)
  lazy val membersMaps : Map[String, ArrayBuffer[MemberWrapperBaseModel]] =
    ExtractHelper.getFromEventualResult(
      ReflectionHelper
        .classTagHelper
        .getEventualMembersMap(classMembersInfo))
      .asScala
      .toMap

  lazy val memberWrapperModels : LazyList[MemberWrapperBaseModel] =
    membersMaps
      .flatMap(w => w._2)
      .to(LazyList)

  lazy val membersIterable : Iterable[Member] =
    memberWrapperModels.map(memberWrapperBaseModel => memberWrapperBaseModel.member)

  val classMembersInfo : ClassMembersInfoBaseModel[T]
}
