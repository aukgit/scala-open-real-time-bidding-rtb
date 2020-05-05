package shared.com.ortb.executors.codegen

import slick.codegen.SourceCodeGenerator
import slick.{ model => m }

class CustomSourceCodeGenerator(model : m.Model) extends SourceCodeGenerator(model) {

  // add some custom imports
  // TODO: fix these imports to refer to your JdbcSupport and your Joda imports
  override def code : String = "import com.github.tototoshi.slick.PostgresJodaSupport._\n" +
    "import org.joda.time.DateTime\n" +
    super.code

  override def Table = new Table(_) {
    override def Column = new Column(_) {

      // munge rawType -> SQL column type HERE (scaladoc in Slick 2.1.0 is outdated or incorrect, GeneratorHelpers#mapJdbcTypeString does not exist)
      // you can filter on model.name for the column name or model.tpe for the column type
      // your IDE won't like the String here but don't worry, the return type the compiler expects here is String
      //      override def rawType : String = model.tpe match {
      //        case "java.sql.Timestamp"               => "DateTime" // kill j.s.Timestamp
      //        case _ => {
      //          //          println(s"${model.table.table}#${model.name} tpe=${model.tpe} rawType=${super.rawType}")
      //          super.rawType
      //        }
      //      }
      super.rawType
    }
  }
}
