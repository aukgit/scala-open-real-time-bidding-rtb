import com.ortb.logger.Logger



import org.apache.logging.log4j.scala.Logging
import org.apache.logging.log4j.Level

object MyClass extends Logging {
  def doStuff(): Unit = {
    logger.info("Doing stuff")
  }

  def doStuffWithUser(): Unit = {
    logger.info(s"Doing stuff with .")
  }
}

object Sample {
  def main(args: Array[String]): Unit = {
    println("Hello World")
    MyClass.doStuff
    Logger.debug("hello debug");
    Logger.error("hello error");
  }
}