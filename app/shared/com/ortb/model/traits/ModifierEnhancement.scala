package shared.com.ortb.model.traits

import java.lang.reflect.Modifier

trait ModifierEnhancement {
  val modifier : Int

  lazy val isPublic : Boolean = Modifier.isPublic(modifier)

  lazy val isPrivate : Boolean = Modifier.isPrivate(modifier)

  lazy val isProtected : Boolean = Modifier.isProtected(modifier)

  lazy val isNonPublic : Boolean = !isPublic
}
