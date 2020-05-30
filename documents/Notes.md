### How to install
- either clone the repo or download as zip
- open with IntelliJ as an SBT project
# scala-rtb-example
 scala real time bidding system using openRTB

## References with Links
- RTB : https://www.smaato.com/real-time-bidding/
- SBT Guide for multiple projects: https://bit.ly/3bkRgy8
- Project Example: https://bit.ly/3bfICkk
- Share code between projects https://bit.ly/2z70jEx
- Multiple Project Sample in Play https://bit.ly/3crrTea
- Sub Projects Example by Play : https://www.playframework.com/documentation/2.8.x/sbtSubProjects
- Sample Shared Project Example: https://bit.ly/2xzhCxz
- Play port change : https://bit.ly/3clAq23 , sbt "run port"
- Organizing Build https://www.scala-sbt.org/0.13/docs/Organizing-Build.html
- Learning Generics: https://gist.github.com/jkpl/5279ee05cca8cc1ec452fc26ace5b68b
- Reflection Programming : https://bit.ly/2XPJo3G
`a(any): A(generic) if classTag[A].runtimeClass.isInstance(a)`
- Class[T] : https://bit.ly/2VqpVF2
- Variance : https://medium.com/@wiemzin/variances-in-scala-9c7d17af9dc4
- Scala Generic with Shapeless: https://meta.plasm.us/posts/2015/11/08/type-classes-and-generic-derivation/
- Reflection (https://bit.ly/2xIQTi5): 
   - https://bit.ly/350JYx7 | 
   - https://bit.ly/3bhknRK : Array kinds match type
   - https://bit.ly/3bwnjem : No TypeTag available for case class Type
   - https://bit.ly/2VBq60l : Scala: convert map to case class
   - https://bit.ly/2x4GHA8 : “No TypeTag available for T” in method using generics
   - https://bit.ly/2yyNH9d : `currentMirror.classSymbol(new A().getClass).toType.members.filter(w => !w.isMethod && w.isPrivate).head`
   - https://bit.ly/2YCJZGi : Getting field names
   - https://bit.ly/2WayLqG : **Get TypeTag[A] from Class[A]**
   - https://bit.ly/2xI8AhL : **How to get TypeTag for generic class at runtime**
   - https://bit.ly/3dniR27 : scala - How to get rid of : class type required but T found
   - https://bit.ly/2yBq5AS : Scala: class type required but {generic type} found in trait -
      
   ```scala
        import scala.slick.driver.MySQLDriver.simple.Tag // here mysql is used , you can import the driver specific to your db
        
        object DAO {
          var db: Database = null
        }
        
        trait CommonAPI[T, A<: Table[T]] {
          private val db = DAO.db
          private val tableTag : Tag => A = _
          def setTag(tag : Tag => A) = { tableTag = tag }
          private val objects = TableQuery(tableTag)
        
          def count: Future[Int] = db.run(objects.length.result)
          def insert(obj: T#TableElementType): Future[Int] = db.run(objects += obj)
          def all: Future[Seq[T]] = db.run(objects.result)
        }  
  ```
- Bytes to object and object to Bytes : https://bit.ly/2SaE9I7 (Serializing)
- Slick Big Table Generate : https://bit.ly/35nyELq
- Slick Generate Table and View Both : https://bit.ly/35qj8Pd
- Sqlite DateTime : https://bit.ly/3deC9GV | https://www.sqlite.org/lang_datefunc.html
- DateTime Joda Circe Encoders : https://bit.ly/2SDSj4E | https://bit.ly/2WzQFm2
- TimeStamp Encoder : https://bit.ly/2Wpq22W
- Slick ORM example : https://scala-slick.org/doc/3.2.0/orm-to-slick.html
- Slick DateTime Attr : https://scala-slick.org/doc/3.3.2/upgrade.html
- Sqlite Mills as Date : https://bit.ly/2SGLBuI | `CAST((julianday('now') - 2440587.5)*86400000 AS INTEGER)` | `CAST(ROUND((julianday('now') - 2440587.5)*86400000) As INTEGER) `
- Circe Enum Json : https://bit.ly/2Wt7jDE | https://bit.ly/3b8vqgh
- Scala Dynamic Type : https://bit.ly/2W9gWID | 
- Circe Optics : https://bit.ly/3b8O5Zh
- Scala nameOf : https://stackoverflow.com/questions/5050682/get-scala-variable-name-at-runtime | https://github.com/dwickern/scala-nameof
- Publish sbt package : https://bit.ly/2WbEqNn
- Pimp My Library (Extension) : https://bit.ly/2WGpkyi | https://bit.ly/2WGpkyi
- Sqlite Expressions : https://www.sqlite.org/lang_expr.html
- Play Error handle : https://bit.ly/2BaSXRt
- Play Status Code : https://bit.ly/2XF6mZk | https://bit.ly/2zGh35Y | https://bit.ly/2B9XFil
- Play Cookies Builder : https://bit.ly/2XB7ltM 

```scala
def anyTypeToByteArray(value: Any): Array[Byte] = {
    val valueConverted :Array[Byte] = SerializationUtils.serialize(value.isInstanceOf[Serializable])
    valueConverted
  }

  def ByteArrayToAny(value: Array[Byte]): Any = {
    val valueConverted: Any = SerializationUtils.deserialize(value)
    valueConverted
  }

```

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

# Important Issue Tracking
- https://github.com/circe/circe/issues/1442

# Libraries to Explore

- https://scalalandio.github.io/chimney/
- https://www.squants.com/
- https://github.com/fthomas/refined