import com.ortb.enumeration.DatabaseActionType
import com.ortb.manager.AppManager
import com.ortb.persistent.repositories.CampaignRepository
import com.ortb.persistent.schema.Tables.CampaignRow
import io.AppLogger
import slick.dbio.Effect
import slick.jdbc.SQLiteProfile.api._
import slick.sql.FixedSqlAction
import scala.reflect.runtime.universe

case class Case(foo: Int) {
  val w = foo
  val w2 = "Hello"
  println("Case Case Instantiated")
}

class Class {
  val w2 = "HellWo"
  println("Class Instantiated")
}

object Inst {

  def apply(className: String, arg: Any) = {
    val runtimeMirror: universe.Mirror = universe.runtimeMirror(getClass.getClassLoader)

    val classSymbol: universe.ClassSymbol = runtimeMirror.classSymbol(Class.forName(className))

    val classMirror: universe.ClassMirror = runtimeMirror.reflectClass(classSymbol)

    if (classSymbol.companion.toString == "<none>") // TODO: use nicer method "hiding" in the api?
    {
      println(s"Info: $className has no companion object")
      val constructors = classSymbol.typeSignature.members.filter(_.isConstructor).toList
      if (constructors.length > 1) {
        println(s"Info: $className has several constructors")
      }
      else {
        val constructorMirror = classMirror.reflectConstructor(constructors.head.asMethod) // we can reuse it
        constructorMirror()
      }

    }
      else
      {
        val companionSymbol = classSymbol.companion
        println(s"Info: $className has companion object $companionSymbol")
        // TBD
      }

  }
}

object app extends App {
  val c = Inst("Class", "")
  val cc = Inst("Case", "")
  val appManager = Inst("AppManager", "")
  println(c)
}

object Invocation
  extends AnyRef

object Application {
  def main(args : Array[String]) : Unit = {
    val appManager = new AppManager

    // AppLogger.info("Help", 3, false)

    class Obj { private def foo(x: Int, y: String): Long = x + y.length }
    import scala.reflect.runtime.{universe => ru}
    def getTypeTag[T: ru.TypeTag](obj: T) = ru.typeTag[T]


    val x = getTypeTag(appManager)
    println(x)
    println(x.tpe)
  }
}
