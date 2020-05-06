package shared.com.ortb.executors.codegen

import slick.codegen.SourceCodeGenerator
import slick.{ model => m }

class CustomSourceCodeGenerator(model : m.Model) extends SourceCodeGenerator(model) {
  // Example : https://scala-slick.org/doc/3.3.0/code-generation.html#customization
  override def code : String = "import com.github.tototoshi.slick.PostgresJodaSupport._\n" +
    "import org.joda.time.DateTime\n" +
    super.code

  override def Table = new Table(_) {
    override def Column = new Column(_) {
      override def rawType : String = model.tpe match {
        case "java.sql.Timestamp" => "java.time.Instant" // kill j.s.Timestamp
        case _ => {
          // println(s"${ model.table.table }#${ model.name } tpe=${ model.tpe } rawType=${ super.rawType }")
          super.rawType
        }
      }
    }
  }
}
