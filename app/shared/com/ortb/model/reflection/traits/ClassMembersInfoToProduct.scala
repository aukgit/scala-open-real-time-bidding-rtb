package shared.com.ortb.model.reflection.traits

import shared.io.helpers.CastingHelper

trait ClassMembersInfoToProduct[T] {
  lazy val isProduct : Boolean =
    classMembersInfo
      .classType
      .getAnnotatedInterfaces
      .exists(w => w.isInstanceOf[Product])
  lazy val toProduct : Option[Product] =
    CastingHelper
      .toProduct(classMembersInfo.entity.orNull)
  val classMembersInfo : ClassMembersInfoBaseModel[T]
}
