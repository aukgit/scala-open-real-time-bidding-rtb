package shared.com.ortb.model

import java.lang.reflect.Member

import shared.io.helpers.EmptyValidateHelper

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

  def getMethods(name : String, parameterCount : Int) : Iterable[MethodWrapperModel] = {
    //noinspection DuplicatedCode
    if (!methodsInfo.hasItem) {
      return Iterable.empty
    }

    val filteredMethods = methods(name)
    filteredMethods.filter(w => w.parameterCount == parameterCount)
  }

  def getMethodsByParameter(name : String, parameterCount : Int) : Iterable[MethodWrapperModel] = {
    //noinspection DuplicatedCode
    if (!methodsInfo.hasItem) {
      return Iterable.empty
    }

    val filteredMethods = methods(name)
    filteredMethods.filter(w => w.parameterCount == parameterCount)
  }
}
