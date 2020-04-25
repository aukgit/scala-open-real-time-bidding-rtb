package services

import com.google.inject.Inject
import services.core.AbstractBasicPersistentService
import shared.com.ortb.persistent.repositories._
import shared.com.ortb.persistent.schema.Tables._

class TransactionService @Inject()(
  injectedRepository : TransactionRepository)
  extends AbstractBasicPersistentService[Transaction, TransactionRow, Int] {

  val repository = injectedRepository
}
