package controllers.v1.apis

import controllers.webapi.core.AbstractRestWebApi
import javax.inject.Inject
import play.api.mvc._
import services.core.AbstractBasicPersistentService
import services._
import shared.com.ortb.persistent.schema.Tables._

class BidRequestApiController @Inject()(
  injectedService : BidRequestService,
  components : ControllerComponents)
  extends AbstractRestWebApi[Bidrequest,  BidrequestRow, Int](components) {

  override val service = injectedService
}
