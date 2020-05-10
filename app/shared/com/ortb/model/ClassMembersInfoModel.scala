package shared.com.ortb.model

import java.lang.reflect.{ Member, Parameter }

import shared.io.helpers.EmptyValidateHelper
import shared.io.helpers.traits.ParameterCompareHelper

import scala.collection.mutable.ArrayBuffer

case class ClassMembersInfoModel(
  classType : Class[_],
  fields : Map[String, FieldWrapperModel],
  methods : Map[String, ArrayBuffer[MethodWrapperModel]],
  constructors : Map[Int, ArrayBuffer[ConstructorWrapperModel]],
  members : Map[String, ArrayBuffer[MemberWrapperModel]],
  totalMembers : Int) {

  lazy val memberWrapperModels : Iterable[MemberWrapperModel] = members.values.flatten
  lazy val membersIterable : Iterable[Member] = memberWrapperModels.map(w => w.member)

  lazy val fieldsInfo : ItemsExistence[FieldWrapperModel] = new ItemsExistence[FieldWrapperModel] {
    lazy override val hasItem : Boolean = EmptyValidateHelper.hasAnyItemDirect(fields)
    lazy override val iterable : Iterable[FieldWrapperModel] = fields.values
  }

  lazy val methodsInfo : ItemsExistence[MethodWrapperModel] = new ItemsExistence[MethodWrapperModel] {
    lazy override val hasItem : Boolean = EmptyValidateHelper.hasAnyItemDirect(methods)
    lazy override val iterable : Iterable[MethodWrapperModel] = methods.values.flatten
  }

  lazy val constructorsInfo : ItemsExistence[ConstructorWrapperModel] = new ItemsExistence[ConstructorWrapperModel] {
    lazy override val hasItem : Boolean = EmptyValidateHelper.hasAnyItemDirect(constructors)
    lazy override val iterable : Iterable[ConstructorWrapperModel] = constructors.values.flatten
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

  def getMethods(name : String, parameterCount : Int) : Iterable[MethodWrapperModel] = {
    //noinspection DuplicatedCode
    val methodsByKey = getMethods(name)

    if (EmptyValidateHelper.isItemsEmptyDirect(methodsByKey)) {
      return methodsByKey
    }

    methodsByKey.filter(w => w.parameterCount == parameterCount)
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
}
