package shared.com.ortb.model

import shared.com.ortb.persistent.schema.Tables._

case class BidFailedInfoWithRowsModel(
  attributes : BidFailedInfoModel,
  data : Seq[LostbidRow]
)
