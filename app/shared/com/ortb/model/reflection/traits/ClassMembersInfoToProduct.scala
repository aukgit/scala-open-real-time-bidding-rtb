package shared.com.ortb.model.reflection.traits

import shared.com.ortb.model.reflection.ProductInfoModel
import shared.io.helpers.{ CastingHelper, ReflectionHelper }

trait ClassMembersInfoToProduct[T] {
  lazy val isProduct : Boolean =
    classMembersInfo
      .classType
      .getInterfaces
      .contains(classOf[Product])

  /**
   * Doesn't throw on fail.
   */
  lazy val toSafeProduct : Option[Product] =
    CastingHelper
      .toProduct(classMembersInfo.entity.orNull)
  /**
   * Doesn't throw on fail.
   */
  lazy val toProductInfoModel : Option[ProductInfoModel] =
    ReflectionHelper.getProductInfoModel(toSafeProduct.get)

  /**
   * Doesn't throw on fail.
   */
  lazy val toProductFieldNames : Option[Iterable[String]] =
    ReflectionHelper.getFieldsNamesOfProductOrCaseClass(toSafeProduct.get)

  val classMembersInfo : ClassMembersInfoBaseModel[T]
}
