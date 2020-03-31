import com.ortb.logger.Logger

object Sample {
  def main(args: Array[String]): Unit = {
    println("Hello World")
    Logger.debug("hello debug");
    Logger.error("hello error");
  }
}