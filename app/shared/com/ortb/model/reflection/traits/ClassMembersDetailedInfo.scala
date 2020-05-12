package shared.com.ortb.model.reflection.traits

import shared.com.ortb.model.reflection.{ ConstructorWrapperModel, FieldWrapperModel, MethodWrapperModel }
import shared.com.ortb.model.traits.ItemsExistence
import shared.io.helpers.EmptyValidateHelper

import scala.collection.Iterable

trait ClassMembersDetailedInfo[T] {
  lazy val fieldsInfo : ItemsExistence[FieldWrapperModel] = new ItemsExistence[FieldWrapperModel] {
    lazy override val hasItem : Boolean = EmptyValidateHelper.hasAnyItemDirect(classMembersInfo.fields)
    lazy override val iterable : Iterable[FieldWrapperModel] = classMembersInfo.fields.values.flatten
  }
  lazy val methodsInfo : ItemsExistence[MethodWrapperModel] = new ItemsExistence[MethodWrapperModel] {
    lazy override val hasItem : Boolean = EmptyValidateHelper.hasAnyItemDirect(classMembersInfo.methods)
    lazy override val iterable : Iterable[MethodWrapperModel] =
      classMembersInfo
        .methods.values.flatten
  }
  lazy val constructorsInfo : ItemsExistence[ConstructorWrapperModel] = new ItemsExistence[ConstructorWrapperModel] {
    lazy override val hasItem : Boolean = EmptyValidateHelper.hasAnyItemDirect(classMembersInfo.constructors)
    lazy override val iterable : Iterable[ConstructorWrapperModel] = classMembersInfo.constructors.values.flatten
  }

  val classMembersInfo : ClassMembersInfoBaseModel[T]
}
