package services

import com.google.inject.Inject
import services.core.AbstractBasicPersistentService
import shared.com.ortb.persistent.repositories._
import shared.com.ortb.persistent.schema.Tables._
import shared.com.repository.RepositoryBase
class KeywordService @Inject()(
  injectedRepository : KeywordRepository)
  extends AbstractBasicPersistentService[Keyword, KeywordRow, Int] {

  val repository = injectedRepository
}
