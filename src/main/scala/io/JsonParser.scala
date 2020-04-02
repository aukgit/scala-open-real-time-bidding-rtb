package io

import io.circe.generic.auto._
import io.circe.parser._
import io.circe.syntax._



object JsonParser {
  def main(path: String): Any = {
    //
    val foo: Foo = Qux(13, Some(14.0))

    val json = foo.asJson.noSpaces
    println(json)

    val decodedFoo = decode[Foo](json)
    println(decodedFoo)
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
  }
}


sealed trait Foo
case class Bar(xs: Vector[String]) extends Foo
case class Qux(i: Int, d: Option[Double]) extends Foo
