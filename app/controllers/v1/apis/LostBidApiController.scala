package controllers.v1.apis

import shared.com.ortb.webapi.core.AbstractRestWebApi
import javax.inject.Inject
import play.api.mvc._
import services._
import shared.com.ortb.persistent.schema.Tables._

class LostBidApiController @Inject()(
  injectedService : LostBidService,
  components      : ControllerComponents)
  extends AbstractRestWebApi[Lostbid, LostbidRow, Int](components) {

  override val service = injectedService
}
