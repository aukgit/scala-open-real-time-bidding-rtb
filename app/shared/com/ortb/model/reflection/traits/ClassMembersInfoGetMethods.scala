package shared.com.ortb.model.reflection.traits

import java.lang.reflect.Parameter

import shared.com.ortb.model.reflection.MethodWrapperModel
import shared.io.helpers.{ EmptyValidateHelper, ParameterCompareHelper }

import scala.collection.Iterable

trait ClassMembersInfoGetMethods {
  val classMembersInfo : ClassMembersInfoBaseModel

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
    if (!classMembersInfo.methodsInfo.hasItem) {
      return Iterable.empty
    }

    if (!classMembersInfo.methods.contains(name)) {
      return Iterable.empty[MethodWrapperModel]
    }

    classMembersInfo.methods(name)
  }
}
