import com.ortb.general.AppConstants
import com.ortb.manager.{ConfigurationManager, AppManager}
import com.ortb.model.config.ConfigModel
import io.{AppLogger, JsonParser, File}
import io.sentry.Sentry
import io.circe._
import io.circe.generic.auto._
import io.circe.parser._
import io.circe.syntax._

object Application {
  def main(args: Array[String]): Unit = {
    Sentry.init(AppConstants.SentryDSN)
    val appManager = new AppManager()

    AppLogger.info("Help", isPrintStack = true)
    println(appManager.config.asJson.noSpaces);


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
