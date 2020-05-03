package shared.com.ortb.model.auctionbid

import shared.com.ortb.model.config.RangeModel
import shared.com.ortb.persistent.schema.Tables._

import scala.util.Random

case class BidFailedInfoModel(
  lastLostBid : LostbidRow,
  lastWinningBid : WinningpriceinfoviewRow,

  lastLosingPrice : Double,
  lastWiningPrice : Double,

  /**
   * By Config Limit
   */
  averageOfLosingPrices : Double,

  /**
   * By Config Limit
   */
  averageOfWiningPrices : Double,

  absoluteDifferenceOfAverageLosingAndWinningPrice : Double,
  absoluteDifferenceOfLosingAndWinningPrice : Double,
  staticIncrement : Double
) {
  /**
   * Returns a random number from 0 to absoluteDifferenceOfAverageLosingAndWinningPrice
   * If (absoluteDifferenceOfAverageLosingAndWinningPrice <= 0) then returns staticIncrement
   * @return
   */
  def randomNumberBetweenAverageLosingAndWinningPriceOrStaticIncrementIfNoDifference : Double = {
    if(absoluteDifferenceOfAverageLosingAndWinningPrice <= 0){
      return staticIncrement
    }

    Random.between(
      0,
      absoluteDifferenceOfAverageLosingAndWinningPrice)
  }
}
