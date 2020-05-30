package shared.io.extensions.traits.primitiveTypes

trait TypeConvertInteger {
  protected val i : Int

  lazy val toStringSome : Option[String] = Some(i.toString)

  def getOnZero(onTrueInt : Int = 0) : Int = {
    getIf(0, onTrueInt)
  }

  def getOnZeroOrNegative(onTrueInt : Int = 0) : Int = {
    getIfEqualOrLess(0, onTrueInt)
  }

  def getIfEqualOrLess(equalsTo : Int, onTrueInt : Int = 0) : Int = {
    if (i <= equalsTo) {
      return onTrueInt
    }

    i
  }

  def getIf(equalsTo : Int, onTrueInt : Int = 0) : Int = {
    if (i == equalsTo) {
      return onTrueInt
    }

    i
  }

  def toBoolString : String = toBoolean.toString

  def toBoolean : Boolean = if (i <= 0) false else true
}


