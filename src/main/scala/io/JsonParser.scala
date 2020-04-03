package io


object JsonParser {
  val fileReader = new FileReader();

  def toJsonFromPath[Type >: Null](path: String): Type = {
    try{
      val getContents = fileReader.getContents(path)
      if()
    }

    //
//    val foo: Foo = Qux(13, Some(14.0))
//
//    val json = foo.asJson.noSpaces
//    println(json)
//
//    val decodedFoo = decode[Foo](json)
//    println(decodedFoo)
//    val json = foo.asJson.noSpaces
//    println(json)
//
//    val decodedFoo = decode[Foo](json)
//    println(decodedFoo)
//    try {
//      val source = scala.io.Source.fromFile("yourFile.txt")
//      val lines = try source.mkString finally source.close()
//      return lines.parseJson;
//    } catch {
//      case e: Exception =>
//        Logger.error(e)
//    }
    return null
  }
}


sealed trait Foo
case class Bar(xs: Vector[String]) extends Foo
case class Qux(i: Int, d: Option[Double]) extends Foo
