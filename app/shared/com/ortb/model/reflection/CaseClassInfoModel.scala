package shared.com.ortb.model.reflection

import shared.com.ortb.model.traits.ItemsExistence
import shared.io.helpers.EmptyValidateHelper

case class CaseClassInfoModel(
  caseFieldModel : List[CaseClassFieldModel]) extends ItemsExistence[CaseClassFieldModel] {
  lazy val hasItem : Boolean = EmptyValidateHelper.hasAnyItemDirect(caseFieldModel)
  lazy val iterable : Iterable[CaseClassFieldModel] = caseFieldModel
}
