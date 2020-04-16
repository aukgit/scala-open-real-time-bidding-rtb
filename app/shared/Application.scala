import com.ortb.enumeration.DatabaseActionType
import com.ortb.manager.AppManager
import com.ortb.persistent.repositories.CampaignRepository
import com.ortb.persistent.schema.Tables.CampaignRow
import io.AppLogger
import slick.dbio.Effect
import slick.jdbc.SQLiteProfile.api._
import slick.sql.FixedSqlAction
import scala.reflect.runtime.universe

object Application {
  def main(args: Array[String]): Unit = {
    val appManager = new AppManager

  }
}
