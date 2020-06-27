package shared.com.ortb.model.routing

case class PlaceHolderRoutingDetailsModel(
  routingMap : Map[String, String]
) {
  lazy val routeKeys : Array[String] = routingMap.keys.toArray
  lazy val routeValues : Array[String] = routingMap.values.toArray

  def getValueByIndex(i : Int) : String = routeValues(i)
}
