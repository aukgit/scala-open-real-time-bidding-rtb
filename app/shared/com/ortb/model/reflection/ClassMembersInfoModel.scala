package shared.com.ortb.model.reflection

import java.lang.reflect.{ Member, Parameter }

import shared.com.ortb.model.results.ResultWithCountSuccessModel
import shared.com.ortb.model.traits.ItemsExistence
import shared.io.helpers.{ EmptyValidateHelper, ExtractHelper, ParameterCompareHelper, ReflectionHelper }

import scala.collection._
import scala.collection.mutable.ArrayBuffer
import scala.concurrent.Future
import scala.jdk.CollectionConverters._

trait ClassMembersInfoBaseModel {
  val classType : Class[_]
  val fields : Map[String, ArrayBuffer[FieldWrapperModel]]
  val methods : Map[String, ArrayBuffer[MethodWrapperModel]]
  val constructors : Map[Int, ArrayBuffer[ConstructorWrapperModel]]
}

case class ClassMembersInfoBaseImplementationModel(
  classType : Class[_],
  fields : Map[String, ArrayBuffer[FieldWrapperModel]],
  methods : Map[String, ArrayBuffer[MethodWrapperModel]],
  constructors : Map[Int, ArrayBuffer[ConstructorWrapperModel]]
) extends ClassMembersInfoBaseModel

case class ClassMembersInfoModel(
  classType : Class[_],
  fields : Map[String, ArrayBuffer[FieldWrapperModel]],
  methods : Map[String, ArrayBuffer[MethodWrapperModel]],
  constructors : Map[Int, ArrayBuffer[ConstructorWrapperModel]],
  eventualMembers : Future[ResultWithCountSuccessModel[scala.Array[MemberWrapperBaseModel]]])
  extends ClassMembersInfoBaseModel {

  lazy val membersMaps : Map[String, ArrayBuffer[MemberWrapperBaseModel]] =
    ExtractHelper.getFromEventualResult(
      ReflectionHelper
        .classTagHelper
        .getEventualMembersMap(this))
      .asScala
      .toMap

  lazy val memberWrapperModels : LazyList[MemberWrapperBaseModel] =
    ExtractHelper.getFromEventualResult(eventualMembers).to(LazyList)

  lazy val membersIterable : Iterable[Member] =
    memberWrapperModels.map(w => w.member)

  lazy val fieldsInfo : ItemsExistence[FieldWrapperModel] = new ItemsExistence[FieldWrapperModel] {
    lazy override val hasItem : Boolean = EmptyValidateHelper.hasAnyItemDirect(fields)
    lazy override val iterable : Iterable[FieldWrapperModel] = fields.values.flatten
  }

  lazy val methodsInfo : ItemsExistence[MethodWrapperModel] = new ItemsExistence[MethodWrapperModel] {
    lazy override val hasItem : Boolean = EmptyValidateHelper.hasAnyItemDirect(methods)
    lazy override val iterable : Iterable[MethodWrapperModel] = methods.values.flatten
  }

  lazy val constructorsInfo : ItemsExistence[ConstructorWrapperModel] = new ItemsExistence[ConstructorWrapperModel] {
    lazy override val hasItem : Boolean = EmptyValidateHelper.hasAnyItemDirect(constructors)
    lazy override val iterable : Iterable[ConstructorWrapperModel] = constructors.values.flatten
  }

  def getMethodsByParameter(name : String, parameters : Array[Parameter])
  : Iterable[MethodWrapperModel] = {
    //noinspection DuplicatedCode
    val methods = getMethods(name, parameters.length)

    if (EmptyValidateHelper.isItemsEmptyDirect(methods)) {
      return methods
    }

    methods.filter(methodWrapperModel =>
      ParameterCompareHelper.isParametersEquivalent(
        methodWrapperModel.parameters,
        parameters))
  }

  def getMethods(name : String, parameterCount : Int) : Iterable[MethodWrapperModel] = {
    //noinspection DuplicatedCode
    val methodsByKey = getMethods(name)

    if (EmptyValidateHelper.isItemsEmptyDirect(methodsByKey)) {
      return methodsByKey
    }

    methodsByKey.filter(w => w.parameterCount == parameterCount)
  }

  def getMethods(name : String) : Iterable[MethodWrapperModel] = {
    //noinspection DuplicatedCode
    if (!methodsInfo.hasItem) {
      return Iterable.empty
    }

    if (!methods.contains(name)) {
      return Iterable.empty[MethodWrapperModel]
    }

    methods(name)
  }
}
