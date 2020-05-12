package services

import com.google.inject.Inject
import services.core.AbstractBasicPersistentService
import shared.com.ortb.persistent.repositories._
import shared.com.ortb.persistent.schema.Tables._

class ContentContextService @Inject()(
  injectedRepository : ContentContextRepository)
  extends AbstractBasicPersistentService[Contentcontext, ContentcontextRow, Int] {

  val repository = injectedRepository
}
