package shared.com.ortb.model.reflection

import shared.com.ortb.model.traits.ItemsExistence
import shared.io.helpers.EmptyValidateHelper

case class ProductInfoModel(
  productFieldModels : Array[ProductFieldModel]) extends ItemsExistence[ProductFieldModel] {
  lazy val hasItem : Boolean = EmptyValidateHelper.hasAnyItemDirect(productFieldModels)
  lazy val map : Map[String, ProductFieldModel] = productFieldModels.map(w => w.name -> w).toMap
  lazy val iterable : Iterable[ProductFieldModel] = productFieldModels
}
