package services

import com.google.inject.Inject
import services.core.AbstractBasicPersistentService
import shared.com.ortb.persistent.repositories._
import shared.com.ortb.persistent.schema.Tables._

class KeywordAdvertiseMappingService @Inject()(
  injectedRepository : KeywordAdvertiseMappingRepository)
  extends AbstractBasicPersistentService[Keywordadvertisemapping, KeywordadvertisemappingRow, Int] {

  val repository = injectedRepository
}
