### How to install
- either clone the repo or download as zip
- open with IntelliJ as an SBT project
# scala-rtb-example
 scala real time bidding system using openRTB

# Links
- https://bit.ly/3ebe30Y
- IAB Process: https://www.youtube.com/watch?v=-Glgi9RRuJs
- Example of RTB from FullStack Academy : https://www.youtube.com/watch?v=0pAseiuSzYw
- https://www.youtube.com/watch?v=-DiIsrJUsRU
- https://www.smaato.com/real-time-bidding/
- 

## References
- SBT Guide for multiple projects: https://bit.ly/3bkRgy8
- Project Example: https://bit.ly/3bfICkk
- Share code between projects https://bit.ly/2z70jEx
- Multiple Project Sample in Play https://bit.ly/3crrTea
- Sub Projects Example by Play : https://www.playframework.com/documentation/2.8.x/sbtSubProjects
- Sample Shared Project Example: https://bit.ly/2xzhCxz
- Play pot change : https://bit.ly/3clAq23 , sbt "run port"
- Organizing Build https://www.scala-sbt.org/0.13/docs/Organizing-Build.html
- Learning Generics: https://gist.github.com/jkpl/5279ee05cca8cc1ec452fc26ace5b68b
- Reflection Programming : https://bit.ly/2XPJo3G
`a(any): A(generic) if classTag[A].runtimeClass.isInstance(a)`
- Class[T] : https://bit.ly/2VqpVF2
- Variance : https://medium.com/@wiemzin/variances-in-scala-9c7d17af9dc4
- Scala Generic with Shapeless: https://meta.plasm.us/posts/2015/11/08/type-classes-and-generic-derivation/

## Logger References
- Example of logging : https://bit.ly/2RNFowy
- Slick logger disable : https://bit.ly/3erk9KZ
- Logger configuration examples : https://bit.ly/2XIj0bO
```scala

object Cmd extends CommandUtils {
  def commands: Map[String, () => Command] = Map(
    "archive" -> ArchiveCommand,
    "convert-month" -> ConvertMonthCommand,
    "convert-year" -> ConvertYearCommand,
    "get" -> GetCommand.apply,
    "info" -> InfoCommand,
    "update" -> UpdateCommand
  )

  def run(args: Array[String], isDev: Boolean): Unit = {
    if (args.length < 1) {
      println("Usage: <command> [parameters...]")
      println("Available commands: " + commands.keys.toVector.sorted.mkString(", "))
      sys.exit(-1)
    }

    val cmdName = args(0)
    val cmd: Command = commands.getOrElse(cmdName, exitError("Unknown command: " + cmdName))()

    configureLogback(
      if (isDev) "logback-dev.xml"
      else if (cmd.isVerbose) "logback-prod-verbose.xml" else "logback-prod.xml")

    val params: Array[String] = args.drop(1)
    val log: Logger = LoggerFactory.getLogger("main")
    log.info("Run: " + args.mkString(" "))
    cmd.run(log, params)
  }

  private def configureLogback(configFilename: String): Unit = {
    val configurator: JoranConfigurator = new JoranConfigurator
    val ctx: LoggerContext = LoggerFactory.getILoggerFactory.asInstanceOf[LoggerContext]
    configurator.setContext(ctx)
    ctx.reset()
    configurator.doConfigure(getClass.getClassLoader.getResource(configFilename))
  }

  def main(args: Array[String]) {
    run(args, isDev = false)
  }
} 

```


## Circle JSON Example
- https://www.programcreek.com/scala/io.circe.Encoder

```scala

import io.circe.{ Decoder, Encoder }
import shapeless.Unwrapped

trait AnyValCirceEncoding {
  implicit def anyValEncoder[V, U](implicit ev: V <:< AnyVal,
                                   V: Unwrapped.Aux[V, U],
                                   encoder: Encoder[U]): Encoder[V] = {
    val _ = ev
    encoder.contramap(V.unwrap)
  }

  implicit def anyValDecoder[V, U](implicit ev: V <:< AnyVal,
                                   V: Unwrapped.Aux[V, U],
                                   decoder: Decoder[U]): Decoder[V] = {
    val _ = ev
    decoder.map(V.wrap)
  }
}

object AnyValCirceEncoding extends AnyValCirceEncoding

object CirceSupport
    extends de.heikoseeberger.akkahttpcirce.FailFastCirceSupport
    with AnyValCirceEncoding 
```