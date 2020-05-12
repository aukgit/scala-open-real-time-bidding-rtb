package shared.com.ortb.model.reflection

import shared.com.ortb.model.traits.ItemsExistence
import shared.io.helpers.EmptyValidateHelper

case class ProductInfoModel(
  productFieldModel : List[ProductFieldModel]) extends ItemsExistence[ProductFieldModel] {
  lazy val hasItem : Boolean = EmptyValidateHelper.hasAnyItemDirect(productFieldModel)
  lazy val iterable : Iterable[ProductFieldModel] = productFieldModel
}
