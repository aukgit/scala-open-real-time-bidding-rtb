package shared.io.extensions.traits.primitiveTypes

trait TypeConvertBoolean {
  protected val b : Boolean

  def toIntString : String = toInt.toString

  def toInt : Int = if (b) 1 else 0

  override def toString : String = if (b) "true" else "false"
}
