package shared.com.ortb.model

import java.lang.reflect.Field

case class FieldWrapperModel(
  field : Field)
  extends MemberWrapperModel(field)
