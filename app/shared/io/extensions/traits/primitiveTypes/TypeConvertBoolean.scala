package shared.io.extensions.traits.primitiveTypes

trait TypeConvertBoolean {
  protected val b : Boolean

  lazy val toIntString : String = toBoolInt.toString

  lazy val toBoolInt : Int = if (b) 1 else 0

  lazy val toBoolString : String = if (b) "true" else "false"

  lazy val toBoolIntSome : Option[Int] = Some(toBoolInt)
}
