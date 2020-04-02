import io.{AppLogger, JsonParser}
import io.sentry.Sentry

object Application {
  val sentryDsn = "https://3540a18396eb4373b3c843b149c55f5d@sentry.io/5183951";

  def main(args: Array[String]): Unit = {
    Sentry.init(sentryDsn)
    JsonParser.main("");
    println("Hello World")
    AppLogger.debug("hello debug");
    AppLogger.warn("hello Warn");
    AppLogger.error("hello error 2");

//    try {
//      throw new Exception("Wait a little XX")
//    }
//    catch {
//      case e: Exception =>
//        AppLogger.error(e)
//    }
  }
}
