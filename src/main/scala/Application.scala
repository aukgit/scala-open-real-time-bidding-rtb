import com.ortb.general.AppConstants
import com.ortb.model.config.ConfigModel
import io.{AppLogger, File, JsonParser}
import io.sentry.Sentry
import io.circe._
import io.circe.generic.auto._
import io.circe.parser._
import io.circe.syntax._

object Application {
  def main(args: Array[String]): Unit = {
    Sentry.init(AppConstants.SentryDSN)

    sealed trait Foo
    case class Bar(xs: Vector[String]) extends Foo
    case class Qux(i: Int, d: Option[Double]) extends Foo
    println(AppConstants.DefaultConfigFileNameWithExtension)
    println(AppConstants.PathConstants.ResourcePath)
    println(AppConstants.PathConstants.ConfigDefaultPath)
    val foo: Foo = Qux(13, Some(14.0))
    println(AppConstants.PathConstants.WorkingDirectory)
    val json = File.getContentsFromResources("configuration.json")

    println(json)
    val o = decode[ConfigModel](json)
    println(o.getOrElse(null).asJson)

    def x() = decode[ConfigModel](json)

    val data2 =  JsonParser.toObjectFromJSONPath[ConfigModel](
      AppConstants.PathConstants.ConfigDefaultPath,
      x)
    println(data2)

//    val decodedFoo = decode[Foo](json)
//    println(decodedFoo)

//    val c = new Configuration();
//    val config = c.getConfig("configuration.json")

//    try {
//      throw new Exception("Wait a little XX")
//    }
//    catch {
//      case e: Exception =>
//        AppLogger.error(e)
//    }
  }
}
