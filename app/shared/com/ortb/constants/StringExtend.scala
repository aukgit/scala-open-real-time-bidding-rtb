package shared.com.ortb.constants

object StringExtend {
  def isEmpty(str : Option[String]) : Boolean =
    str.forall(_.trim.isEmpty)

  def isEmpty(str : String) : Boolean = str.isEmpty

  def hasCharacter(str : Option[String]) : Boolean =
    str.exists(_.trim.nonEmpty)
}
