package shared.com.ortb.model.reflection

import java.lang.reflect.Field

case class FieldWrapperModel(
  field : Field)
  extends MemberWrapperModel(field)
